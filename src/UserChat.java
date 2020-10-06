import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserChat {
    private final ArrayList<ArrayList<Boolean>> patternStore;
    private final ChatCommunication chatCommunication;
    private final CheckBoxGrid checkBoxGrid;
    private final MusicPlayer musicPlayer;
    private final JTextField comment;
    private final DefaultListModel<String> discussionElements;
    private final JList<String> discussion;

    public UserChat(CheckBoxGrid checkBoxGrid, MusicPlayer musicPlayer) {
        this.chatCommunication = new ChatCommunication();
        this.checkBoxGrid = checkBoxGrid;
        this.patternStore = new ArrayList<>();
        this.musicPlayer = musicPlayer;

        comment = new JTextField("Your comment", 20);
        comment.addActionListener(new UserMakesCommentListener());

        discussionElements = new DefaultListModel<>();
        discussion = new JList<>(discussionElements);
        discussion.addListSelectionListener(new UserMakesSelectionListener());
    }

    public JTextField getCommentComponent() {
        return comment;
    }

    public JList<String> getDiscussionComponent() {
        return discussion;
    }

    public void startReaderThread() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    class UserMakesCommentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            chatCommunication.sendPattern(comment.getText(), checkBoxGrid);
        }
    }

    class UserMakesSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                musicPlayer.stop();
                System.out.println(discussion.getSelectedIndex());
                checkBoxGrid.updateUI(patternStore.get(discussion.getSelectedIndex()));
            }
        }
    }

    class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = chatCommunication.readIncomingComment()) != null) {
                    discussionElements.addElement(message);
                    patternStore.add(chatCommunication.readIncomingPattern());
                    System.out.println("Read: " + message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



