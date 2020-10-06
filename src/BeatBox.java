import javax.swing.*;

public class BeatBox {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                createAndLaunchGui();
            }
        });
    }

    private static void createAndLaunchGui() {
        MainGUI mainGui = new MainGUI();
        CheckBoxGrid checkBoxGrid = new CheckBoxGrid();
        MusicPlayer musicPlayer = new MusicPlayer();
        UserChat userChat = new UserChat(checkBoxGrid, musicPlayer);
        userChat.startReaderThread();

        CheckBoxGridPanel checkBoxGridPanel = new CheckBoxGridPanel(checkBoxGrid);
        MusicPlayerControlPanel musicPlayerControlPanel = new MusicPlayerControlPanel(checkBoxGrid, musicPlayer);
        FileStoragePanel fileStoragePanel = new FileStoragePanel(checkBoxGrid, musicPlayer);
        UserChatPanel userChatPanel = new UserChatPanel(userChat);

        mainGui.addMusicControlPanel(musicPlayerControlPanel);
        mainGui.addFileStoragePanel(fileStoragePanel);
        mainGui.addUserChatPanel(userChatPanel);
        mainGui.addGridPanel(checkBoxGridPanel);
        mainGui.launch();
    }
}
