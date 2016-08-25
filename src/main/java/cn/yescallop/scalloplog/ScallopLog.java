package cn.yescallop.scalloplog;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScallopLog extends PluginBase {
    
    private File log;
    
    @Override
    public void onEnable() {
        log = new File(this.getServer().getDataPath() + "/scallop.log");
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                this.getLogger().error(e.getMessage(), e);
                this.setEnabled(false);
            }
        }
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public void logRaw(String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.log, true), StandardCharsets.UTF_8);
            writer.write(format.format(new Date()) + " " + text + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            this.getLogger().error(e.getMessage(), e);
            this.setEnabled(false);
        }
    }
    
    public void logPlayer(Player player, String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.logRaw("[" + player.getName() + "] " + text);
    }
}