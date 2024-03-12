import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.Text;

import javax.swing.*;
public class GUI extends JFrame {

    private String imageDir = null;
    private long unixTime;

    private ImageArea myImageArea = new ImageArea();
    private InputArea myInputArea = new InputArea();
    private ControlArea myControlArea = new ControlArea();
    private BufferedImage bufferedImage;
    private TimeConversion myTimeConversion = new TimeConversion();
    
    GUI() {
        super("貼文製造機");
        setLayout(new BorderLayout());
        setSize(1280, 720);
        setResizable(false);
        add(myImageArea, BorderLayout.WEST);
        add(myInputArea, BorderLayout.CENTER);
        add(myControlArea, BorderLayout.SOUTH);
        setVisible(true);
    }

    private class ImageArea extends JPanel {

        ImageArea() {
            removeImage();
        }

        public void setImage(ImageIcon item) {
            JLabel imageLabel = new JLabel(item);
            removeAll();
            add(imageLabel);
            revalidate();
        }

        public void removeImage() {
            removeAll();
            imageDir = null;
            revalidate();
        }
    }

    private class InputArea extends JTextArea {
        InputArea() {
            setColumns(18);
            setLineWrap(true);
            setVisible(true);
        }

        public void setStr(String str) {
            setText(getText() + str);
        }
    }
    
    public String getImageDir() {
        return imageDir;
    }

    public class ControlArea extends JPanel {
    
        private ImageIcon ImagePreview = new ImageIcon();
        private File file; 
    
        JButton addImageBtn = new JButton("新增圖片", new ImageIcon("addImage.png"));
        JButton deleteImageBtn = new JButton("刪除圖片", new ImageIcon("delete.png"));

        JLabel hintTheme = new JLabel("文章主題:");
        JTextField inputTopic = new JTextField("", 10);
        JLabel hintLength = new JLabel("字數上限:");
        JTextField inputLength = new JTextField("", 4);
        JButton generateBtn = new JButton("文章生成", new ImageIcon("write.png"));

        JLabel scheduleTime = new JLabel("排程日期(YYYYMMDDHHMM):");
        JTextField inputYYYYMMDDHHmm = new JTextField("", 12);
        JButton postBtn = new JButton("fb發佈", new ImageIcon("send.png"));
        BtnHandler myBtnHandler = new BtnHandler();
        Random rand =new Random();
        String arttext;
        int numi;
        JButton igpost = new JButton("ig發佈", new ImageIcon("send.png"));
        ControlArea() {
            setLayout(new FlowLayout());
            setVisible(true);
            
            addImageBtn.addActionListener(myBtnHandler);
            add(addImageBtn);
            deleteImageBtn.addActionListener(myBtnHandler);
            add(deleteImageBtn);
            add(new JLabel("               "));//填充
            add(hintTheme);
            add(inputTopic);
            add(hintLength);
            add(inputLength);
            generateBtn.addActionListener(myBtnHandler);
            postBtn.addActionListener(myBtnHandler);
            igpost.addActionListener(myBtnHandler);
            add(generateBtn);
            add(new JLabel("               "));//填充
            add(scheduleTime);
            add(inputYYYYMMDDHHmm);
            add(postBtn);
            add(igpost);
        }
    
        private class BtnHandler implements ActionListener {
    
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(e.getSource() == addImageBtn) {
                        Random rand =new Random();
                        numi=rand.nextInt(10);
                        // String nums = "you got picture" + numi;
                        JFileChooser imageChooser = new JFileChooser();
                        // JOptionPane.showMessageDialog(null, nums);
                        imageChooser.addChoosableFileFilter(new ImageFilter());
                        imageChooser.setAcceptAllFileFilterUsed(false);
                        imageChooser.showOpenDialog(getParent());
                        file = imageChooser.getSelectedFile();
                        imageDir = file.getAbsolutePath();
                        try {
                            bufferedImage = (BufferedImage)ImageIO.read(file);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        //height - 80
                        Image image = bufferedImage.getScaledInstance(bufferedImage.getWidth() * 640 / bufferedImage.getHeight() , 640, Image.SCALE_SMOOTH);
                        ImagePreview = new ImageIcon(image);
                        myImageArea.setImage(ImagePreview);
                    } else if(e.getSource() == deleteImageBtn) {
                        myImageArea.removeImage();
                        imageDir = null;
                        revalidate();
                    } else if(e.getSource() == generateBtn) {
                        BullShit myBullShit = new BullShit(inputTopic.getText(), Integer.valueOf(inputLength.getText()));
                        arttext = myBullShit.Generate(inputTopic.getText(), Integer.valueOf(inputLength.getText()));
                        myInputArea.setStr(arttext);
                    } else if(e.getSource() == postBtn) { //TODO 排程＆發文
                        
                        unixTime = myTimeConversion.timeConversion(inputYYYYMMDDHHmm.getText());
                        POST_HttpURLConnection posthttp123 = new POST_HttpURLConnection();
                        // if(unixTime < System.currentTimeMillis()+800 / 1000L || unixTime > (System.currentTimeMillis() + 6480000) ) {
                        //     unixTime = System.currentTimeMillis()+800 / 1000L;
                        // }
                        posthttp123.posthandler(numi, myInputArea.getText(), unixTime);
                        System.out.printf("%d",unixTime); 
                        
                    }
                     else if(e.getSource() == igpost) { //TODO 排程＆發文
                       
                        Random rand =new Random();
                        numi=rand.nextInt(2);
                        String nums = "you got picture" + numi;
                      
                    JOptionPane.showMessageDialog(null, nums);
                    // System.out.printf("this is ig"); 
                    POST_HttpURLConnectionIG posthttptest = new POST_HttpURLConnectionIG();
                    posthttptest.posthandler(numi,myInputArea.getText());
                    
                    }
                }
                catch(Exception ex)
                {

                }
        
            }
        
        }
    }
}