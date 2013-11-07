package li.cil.oc.common.tileentity

import li.cil.oc.api
import li.cil.oc.api.Network
import li.cil.oc.api.network.{Visibility, Message}
import li.cil.oc.common.ReleasePressedKeys
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.ForgeSubscribe
import scala.collection.mutable

class Keyboard extends Rotatable with Environment {
  val node = api.Network.newNode(this, Visibility.Network).
    withComponent("keyboard").
    create()

  val pressedKeys = mutable.Map.empty[EntityPlayer, mutable.Map[Integer, Character]]

  // ----------------------------------------------------------------------- //

  override def updateEntity() {
    if (node != null && node.network == null) {
      Network.joinOrCreateNetwork(worldObj, xCoord, yCoord, zCoord)
    }
  }

  // ----------------------------------------------------------------------- //

  override def invalidate() {
    super.invalidate()
    MinecraftForge.EVENT_BUS.unregister(this)
  }

  override def validate() {
    super.validate()
    MinecraftForge.EVENT_BUS.register(this)
  }

  override def onChunkUnload() {
    super.onChunkUnload()
    MinecraftForge.EVENT_BUS.unregister(this)
  }

  @ForgeSubscribe
  def onReleasePressedKeys(e: ReleasePressedKeys) {
    pressedKeys.get(e.player) match {
      case Some(keys) => for ((code, char) <- keys)
        node.sendToReachable("computer.signal", "key_up", char, code, e.player.getCommandSenderName)
      case _ =>
    }
    pressedKeys.remove(e.player)
  }

  // ----------------------------------------------------------------------- //

  override def readFromNBT(nbt: NBTTagCompound) {
    super.readFromNBT(nbt)
    node.load(nbt)
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    super.writeToNBT(nbt)
    node.save(nbt)
  }

  // ----------------------------------------------------------------------- //

  override def onMessage(message: Message) = {
    message.data match {
      case Array(p: EntityPlayer, char: Character, code: Integer) if message.name == "keyboard.keyDown" =>
        if (isUseableByPlayer(p)) {
          pressedKeys.getOrElseUpdate(p, mutable.Map.empty[Integer, Character]) += code -> char
          node.sendToReachable("computer.signal", "key_down", char, code, p.getCommandSenderName)
        }
      case Array(p: EntityPlayer, char: Character, code: Integer) if message.name == "keyboard.keyUp" =>
        pressedKeys.get(p) match {
          case Some(keys) if keys.contains(code) =>
            keys -= code
            node.sendToReachable("computer.signal", "key_up", char, code, p.getCommandSenderName)
          case _ =>
        }
      case Array(p: EntityPlayer, value: String) if message.name == "keyboard.clipboard" =>
        if (isUseableByPlayer(p)) {
          node.sendToReachable("computer.signal", "clipboard", value, p.getCommandSenderName)
        }
      case _ =>
    }
    super.onMessage(message)
  }

  // ----------------------------------------------------------------------- //

  def isUseableByPlayer(p: EntityPlayer) = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
    p.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64
}