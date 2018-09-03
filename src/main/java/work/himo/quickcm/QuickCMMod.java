package work.himo.quickcm;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
@Mod(modid = QuickCMMod.MODID, version = QuickCMMod.VERSION)
public class QuickCMMod {
	public static final String MODID = "QuickCM";
	public static final String VERSION = "1.0";
	public static Properties properties= new Properties();
	public static File propertiesFile = new File("./config/QuickCM.properties");
	public static String[] Displaynamelist = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
	public static String[] Commandnamelist = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
	public static int RenderButton;

	private boolean isOnHypixel;

	@EventHandler
	  public void init(FMLInitializationEvent event) {
	    MinecraftForge.EVENT_BUS.register(this);
	    if(!propertiesFile.exists()) {//フォルダがなかったら
			try {
				propertiesFile.createNewFile();//生成
				properties.setProperty("Displayname", "&a&nRight click to edit,&6solo_insane,&e&lsolo_normal,&6teams_insane,&fteams_normal,&a&n右クリックで編集,,,,,,,,,");
				properties.setProperty("Commandname", "/achat GG,/play solo_insane,/play solo_normal,/play teams_insane,/play teams_normal,/p warp,,,,,,,,,");
				properties.setProperty("RenderButton", "6");

				properties.store(new FileOutputStream(propertiesFile), "Dont change it!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	    try {
			properties.load(new FileInputStream(propertiesFile));
			Displaynamelist = properties.getProperty("Displayname","0").split(",", -1);
			Commandnamelist = properties.getProperty("Commandname","0").split(",", -1);
			RenderButton = Integer.valueOf(properties.getProperty("RenderButton","0"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onActionChatGui(GuiScreenEvent.ActionPerformedEvent.Post e) {
	    if (!isOnHypixel || !(e.gui instanceof GuiChat)){
	      return;
	    }

	    for (int i =0;i<RenderButton;i++) {
	    	if (e.button.id == i) {
	    		Minecraft.getMinecraft().thePlayer.sendChatMessage(Commandnamelist[i]);
	    		Minecraft.getMinecraft().thePlayer.closeScreen();
	    	}
	    }

	    if (e.button.id == 15) {
	    	if (RenderButton > 0) RenderButton = RenderButton - 1;
	    	e.buttonList.get(RenderButton).visible = false;
	    	Displaynamelist[RenderButton] = "";
	    	Commandnamelist[RenderButton] = "";
	    	for (int i=0;i<16;i++) {
	    		if (17+i == 17+RenderButton) {
	    			e.buttonList.get(17+RenderButton).visible = true;
	    			if (i == 0) {
	    				DLBlack[1] = 20 + 22*i + 13;
	    			} else {
	    				DLBlack[1] = 20 + 22*i + 11;
	    			}
	    			DLBlack[0] = e.gui.width - 45;
	    			continue;
	    		}
	    		e.buttonList.get(i+17).visible = false;
	    	}
	    }
	    if (e.button.id == 16) {
	    	if (RenderButton == 15) return;
	    	if (RenderButton < 15) RenderButton = RenderButton + 1;
	    	new GuiQuickCM().display(RenderButton - 1);
	    }
	    if (e.button.id >= 17) {
	    	Minecraft.getMinecraft().thePlayer.closeScreen();
	    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    StringSelection selection = new StringSelection("http://www.himo.work/mods/QuickCommandMod.html");
		    clipboard.setContents(selection, selection);
		    Minecraft.getMinecraft().ingameGUI.displayTitle("Copied download link!!", null, 0, 40, 5);
			Minecraft.getMinecraft().ingameGUI.displayTitle(null, "ダウンロードリンクをコピーしました!!", 0, 40, 5);
			Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, 0, 40, 5);
		    copieddlink();
	    }
	}
	public static void copieddlink() {
		Minecraft.getMinecraft().ingameGUI.displayTitle("Copied download link!!", null, 0, 40, 5);
		Minecraft.getMinecraft().ingameGUI.displayTitle(null, "ダウンロードリンクをコピーしました!!", 0, 40, 5);
		Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, 0, 40, 5);
	}
	int kusakusanokusa = -2;
	@SubscribeEvent
	public void onRenderChatGui(GuiScreenEvent.DrawScreenEvent.Post e) throws IOException {
		if (!isOnHypixel || !(e.gui instanceof GuiChat)){
			return;
	    }
	    Minecraft.getMinecraft().fontRendererObj.drawString("§eQuickCM by §nhimohimo.", e.gui.width - 65 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("QuickCM by himohimo.")/2, e.gui.height - 25, 0xffffffff, true);
	    Minecraft.getMinecraft().fontRendererObj.drawString("§b§lQuick", e.gui.width - 43 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("Quick")/2, 2, 0xffffffff, true);
	    Minecraft.getMinecraft().fontRendererObj.drawString("§b§lCommand", e.gui.width - 43 - Minecraft.getMinecraft().fontRendererObj.getStringWidth("Command")/2, 10, 0xffffffff, true);
	    int x1 = e.gui.width -75;
	    int x2 = e.gui.width -5;
	    for (int i=0;i<RenderButton;i++) {
	    	if(x1 <= e.mouseX && e.mouseX <= x2 && 20 + 22*i <= e.mouseY && e.mouseY <= 40 + 22*i && Mouse.isButtonDown(1)) {
	    		new GuiQuickCM().display(i);
	    	}
	    }
	    if (e.gui.width - 60 <= e.mouseX && e.mouseX <= e.gui.width - 28 && e.gui.height - 25 <= e.mouseY && e.mouseY <= e.gui.height - 15 && Mouse.isButtonDown(0)) {
	    	kusakusanokusa = kusakusanokusa+1;
	    	if (kusakusanokusa >=3) {
	    		kusakusanokusa = -10;
	    		try {
		    		Desktop.getDesktop().browse(new URI("https://twitter.com/intent/user?user_id=823766237096202240"));
		    	} catch (URISyntaxException e1) {
		    	}
	    	}
	    }
	}

	public static String Colorcode(String arg) {
		return arg.replaceAll("&0", "§0").replaceAll("&1", "§1").replaceAll("&2", "§2").replaceAll("&3", "§3").replaceAll("&4", "§4").replaceAll("&5", "§5").replaceAll("&6", "§6").replaceAll("&7", "§7").replaceAll("&8", "§8").replaceAll("&9", "§9").replaceAll("&a", "§a").replaceAll("&b", "§b").replaceAll("&c", "§c").replaceAll("&d", "§d").replaceAll("&e", "§e").replaceAll("&f", "§f").replaceAll("&k", "§k").replaceAll("&l", "§l").replaceAll("&m", "§m").replaceAll("&n", "§n").replaceAll("&o", "§o").replaceAll("&r", "§r");
	}
	int[] DLBlack = {0, 0, 0, 0};
	  @SubscribeEvent
	  public void onInitChatGui(GuiScreenEvent.InitGuiEvent.Post e) {
	    if (!isOnHypixel || !(e.gui instanceof GuiChat)){
	      return;
	    }
	    int y = 20;
	    for (int i=0;i<15;i++) {
	    	e.buttonList.add(new GuiButton(i, e.gui.width - 75, y, 70, 20, Colorcode(Displaynamelist[i])));
		    y += 22;
	    }
	    e.buttonList.add(new GuiButton(15, e.gui.width - 75, 12, 10, 8, "-"));
	    e.buttonList.add(new GuiButton(16, e.gui.width - 15, 12, 10, 8, "+"));
	    e.buttonList.add(new GuiButton(17, e.gui.width - 45, 20, 40, 13, "§e§nDL Link"));//17

	    for (int u=0;u<15;u++) {
	    	e.buttonList.add(new GuiButton(18+u, e.gui.width - 45, 40 + 22*u, 40, 13, "§e§nDL Link"));
	    }
	    for (int i=0;i<15;i++) {
    		if (i < RenderButton) {
    			e.buttonList.get(RenderButton).visible = true;
    			continue;
    		}
    		e.buttonList.get(i).visible = false;
    	}
	    for (int i=0;i<16;i++) {
    		if (i+17 == 17+RenderButton) {
    			e.buttonList.get(17+RenderButton).visible = true;
    			if (i == 0) {
    				DLBlack[1] = 20 + 22*i + 13;
    			} else {
    				DLBlack[1] = 20 + 22*i + 11;
    			}
    			DLBlack[0] = e.gui.width - 45;
    			continue;
    		}
    		e.buttonList.get(i+17).visible = false;
    	}
	  }
	  public static String getIP() {
	        if (Minecraft.getMinecraft().isSingleplayer()) return "localhost";

	        return Minecraft.getMinecraft().getCurrentServerData() == null ? "" : Minecraft.getMinecraft().getCurrentServerData().serverIP;
	  }
	  @SubscribeEvent
	  public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
	    if (getIP().toLowerCase().contains("hypixel") || getIP().equals("localhost") || getIP().equals("172.65.128.55") || getIP().equals("66.70.179.180")) {
	      isOnHypixel = true;
	    }
	  }
	  @SubscribeEvent
	  public void onRenderGameOverlay(TickEvent.RenderTickEvent event){
			if (Minecraft.getMinecraft().currentScreen instanceof GuiChat && isOnHypixel) {
				GuiIngame.drawRect(DLBlack[0], DLBlack[1], DLBlack[0] + 40, DLBlack[1] + 1, 0xFF000000);
				GuiIngame.drawRect(DLBlack[0] - 30, 20, DLBlack[0] - 20, 21, 0xFF000000);
			}
	  }
	  @SubscribeEvent
	  public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
	    isOnHypixel = false;
	  }

}
