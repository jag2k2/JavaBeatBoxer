import javax.swing.*;
import java.awt.*;

public class UserChatPanel {

    private final JPanel panel;

    public UserChatPanel(UserChat userChat) {
        JPanel commentPane = new JPanel(new BorderLayout());
        commentPane.add(BorderLayout.CENTER, userChat.getCommentComponent());

        JScrollPane dScroller = new JScrollPane(userChat.getDiscussionComponent());
        dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        dScroller.setPreferredSize(new Dimension(50, -1));

        panel = new JPanel(new BorderLayout());
        panel.add(BorderLayout.CENTER, dScroller);
        panel.add(BorderLayout.SOUTH, commentPane);
    }
    public JPanel getPanel() {
        return panel;
    }
}
