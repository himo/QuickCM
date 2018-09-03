package work.himo.quickcm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiQuickCM extends GuiScreen {

	private static GuiTextField DisplayName;
	private static GuiTextField CommandName;
	private GuiButton Close;
	private GuiButton Reset;

	private static int Buttonint;


	@Override
	public void initGui() {
		DisplayName = new GuiTextField(0,this.fontRendererObj, this.width / 2 - 75, this.height / 2 - 44, 150, 20);
		if (QuickCMMod.Displaynamelist[Buttonint].equals("")) {
			DisplayName.setText("§7p warp");
		} else {
			DisplayName.setText(QuickCMMod.Displaynamelist[Buttonint]);
		}
		CommandName = new GuiTextField(1,this.fontRendererObj, this.width / 2 - 75, this.height / 2 - 6, 150, 20);
		if (QuickCMMod.Commandnamelist[Buttonint].equals("")) {
			CommandName.setText("§7/p warp");
		} else {
			CommandName.setText(QuickCMMod.Commandnamelist[Buttonint]);
		}
		Reset = new GuiButton(0, this.width / 2 - 75, this.height / 2 + 30, 74, 20, "§cReset");
		Close = new GuiButton(1, this.width / 2 + 1, this.height / 2 + 30, 74, 20, "Done");
		addButtons();
	}
	public void addButtons() {
		buttonList.add(Close);
		buttonList.add(Reset);
	}

	@Override
	public void updateScreen() {
		DisplayName.updateCursorCounter();
		CommandName.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		Minecraft.getMinecraft().fontRendererObj.drawString("QuickCommandMod by himohimo.", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("QuickCommandMod by himohimo.")/2, this.height / 2 - 116, 0xffffffff, true);
		Minecraft.getMinecraft().fontRendererObj.drawString("The original code \"Quick SW\" by Leonardosnt.", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("The original code \\\"Quick SW\\\" by Leonardosnt.\"")/2, this.height / 2 - 96, 0xffffffff, true);
		Minecraft.getMinecraft().fontRendererObj.drawString("DisplayName", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("DisplayName")/2, this.height / 2 - 56, 0xffffffff, true);
		Minecraft.getMinecraft().fontRendererObj.drawString("CommandName", this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("CommandName")/2, this.height / 2 - 18, 0xffffffff, true);
		DisplayName.drawTextBox();
		CommandName.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		DisplayName.mouseClicked(mouseX, mouseY, mouseButton);
		CommandName.mouseClicked(mouseX, mouseY, mouseButton);
		if(DisplayName.isFocused()) {
			if(DisplayName.getText().equals("§7p warp")) {
				DisplayName.setText("");
			}
		} else {
			if(QuickCMMod.Displaynamelist[Buttonint].equals("")) {
				DisplayName.setText("§7p warp");
			}
		}
		if(CommandName.isFocused()) {
			if(CommandName.getText().equals("§7/p warp")) {
				CommandName.setText("");
			}
		} else {
			if(QuickCMMod.Commandnamelist[Buttonint].equals("")) {
				CommandName.setText("§7/p warp");
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(DisplayName.getVisible()) {
			if(DisplayName.isFocused()) {
				DisplayName.textboxKeyTyped(typedChar, keyCode);
				QuickCMMod.Displaynamelist[Buttonint] = DisplayName.getText();
			}
		}
		if(CommandName.getVisible()) {
			if(CommandName.isFocused()) {
				CommandName.textboxKeyTyped(typedChar, keyCode);
				QuickCMMod.Commandnamelist[Buttonint] = CommandName.getText();
			}
		}
		super.keyTyped(typedChar, keyCode);
	}

	public void display(int arg){
		MinecraftForge.EVENT_BUS.register(this);
		Buttonint = arg;
	    initGui();
	}
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		MinecraftForge.EVENT_BUS.unregister(this);
		Minecraft.getMinecraft().displayGuiScreen(this);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	@Override
	public void onGuiClosed() {
		saveConfig();
		super.onGuiClosed();
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			QuickCMMod.Displaynamelist[Buttonint] = "";
			DisplayName.setText("§7p warp");
			QuickCMMod.Commandnamelist[Buttonint] = "";
			CommandName.setText("§7/p warp");
		} else if (button.id == 1) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}

	}

	public static void saveConfig() {
		try {
			QuickCMMod.properties.load(new FileInputStream(QuickCMMod.propertiesFile));
			QuickCMMod.properties.setProperty("Displayname", QuickCMMod.Displaynamelist[0]+","+QuickCMMod.Displaynamelist[1]+","+QuickCMMod.Displaynamelist[2]+","+QuickCMMod.Displaynamelist[3]+","+QuickCMMod.Displaynamelist[4]+","+QuickCMMod.Displaynamelist[5]+","+QuickCMMod.Displaynamelist[6]+","+QuickCMMod.Displaynamelist[7]+","+QuickCMMod.Displaynamelist[8]+","+QuickCMMod.Displaynamelist[9]+","+QuickCMMod.Displaynamelist[10]+","+QuickCMMod.Displaynamelist[11]+","+QuickCMMod.Displaynamelist[12]+","+QuickCMMod.Displaynamelist[13]+","+QuickCMMod.Displaynamelist[14]);
			QuickCMMod.properties.setProperty("Commandname", QuickCMMod.Commandnamelist[0]+","+QuickCMMod.Commandnamelist[1]+","+QuickCMMod.Commandnamelist[2]+","+QuickCMMod.Commandnamelist[3]+","+QuickCMMod.Commandnamelist[4]+","+QuickCMMod.Commandnamelist[5]+","+QuickCMMod.Commandnamelist[6]+","+QuickCMMod.Commandnamelist[7]+","+QuickCMMod.Commandnamelist[8]+","+QuickCMMod.Commandnamelist[9]+","+QuickCMMod.Commandnamelist[10]+","+QuickCMMod.Commandnamelist[11]+","+QuickCMMod.Commandnamelist[12]+","+QuickCMMod.Commandnamelist[13]+","+QuickCMMod.Commandnamelist[14]);
			QuickCMMod.properties.setProperty("RenderButton", String.valueOf(QuickCMMod.RenderButton));
			QuickCMMod.properties.store(new FileOutputStream(QuickCMMod.propertiesFile), "Dont change it!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}



}

