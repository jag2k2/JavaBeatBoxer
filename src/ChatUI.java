import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChatUI {
    private final ChatCommunication serverCommunication;
    private final CheckBoxGrid checkBoxGrid;
    private final ArrayList<ArrayList<Boolean>> patternStore;
    private final MusicPlayer musicPlayer;
    private final JTextField comment;
    private final DefaultListModel<String> discussionElements;
    private final JList<String> discussion;
    private final JScrollPane dScroller;
    private final JPanel commentPane;

    public ChatUI(CheckBoxGrid checkBoxGrid, MusicPlayer musicPlayer) {
        serverCommunication = new ChatCommunication();
        this.checkBoxGrid = checkBoxGrid;
        this.patternStore = new ArrayList<>();
        this.musicPlayer = musicPlayer;

        comment = new JTextField("Your comment", 20);

        discussionElements = new DefaultListModel<>();
        discussion = new JList<>(discussionElements);
        discussion.addListSelectionListener(new UserMakesSelectionListener());

        dScroller = new JScrollPane(discussion);
        dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        dScroller.setPreferredSize(new Dimension(50, -1));

        JButton sendComment = new JButton("Send");
        sendComment.addActionListener(new UserMakesCommentListener());

        BorderLayout commentLayout = new BorderLayout();
        commentPane = new JPanel(commentLayout);
        commentPane.add(BorderLayout.CENTER, comment);
        commentPane.add(BorderLayout.EAST, sendComment);
    }

    public JScrollPane getScrollerPane() {
        return dScroller;
    }

    public JPanel getCommentPane() {
        return commentPane;
    }

    public class UserMakesCommentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            serverCommunication.sendPattern(comment.getText(), checkBoxGrid);
        }
    }

    public class UserMakesSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                musicPlayer.stop();
                System.out.println(discussion.getSelectedIndex());
                checkBoxGrid.updateUI(patternStore.get(discussion.getSelectedIndex()));
            }
        }
    }

    public void startReaderThread() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = serverCommunication.readIncomingComment()) != null) {
                    discussionElements.addElement(message);
                    patternStore.add(serverCommunication.readIncomingPattern());
                    System.out.println("Read: " + message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



