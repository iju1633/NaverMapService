package gui.view;

import gui.domain.Address;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

    private static final String JFRAME_MAP_DESCRIPTION = "지도보기";
    private static final String JFRAME_TITLE_SUBJECT = "Map View";
    private static final String JFRAME_INPUT_ADDRESS = "주소입력";
    private static final String JFRAME_BUTTON_CLICK = "클릭";

    private static final String PRINT_ADDRESS_NAME = "도로명";
    private static final String PRINT_JIBUN_ADDRESS = "지번주소";
    private static final String PRINT_LONGITUDE = "경도";
    private static final String PRINT_LATITUDE = "위도";

    private static final int JFRAME_WINDOW_LENGTH = 50;
    private static final int JFRAME_WINDOW_WIDTH = 730;
    private static final int JFRAME_WINDOW_HEIGHT = 660;
    private static final int JFRAME_PRINT_ADDRESS_WIDTH = 4;
    private static final int JFRAME_PRINT_ADDRESS_HEIGHT = 1;

    private JTextField address;
    private JLabel resAddress; // residence Address
    private JLabel resX;
    private JLabel resY;
    private JLabel imageLabel;
    private JLabel jibunAddress;

    public JTextField getAddress() {
        return address;
    }

    public JLabel getResAddress() {
        return resAddress;
    }

    public JLabel getResX() {
        return resX;
    }

    public JLabel getResY() {
        return resY;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public JLabel getJibunAddress() {
        return jibunAddress;
    }

    public void initGUI() {
        JFrame frame = new JFrame(JFRAME_TITLE_SUBJECT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        JLabel addressLbl = new JLabel(JFRAME_INPUT_ADDRESS);

        initJLabel();

        address = new JTextField(JFRAME_WINDOW_LENGTH);
        JButton button = new JButton(JFRAME_BUTTON_CLICK);
        button.addActionListener(new NaverMapClickListener(this));

        JPanel mapJPanel = initMapJPanel(addressLbl, button);
        JPanel addressJPanel = initAddressJPanel();
        initContainer(container, mapJPanel, addressJPanel);

        frame.setSize(JFRAME_WINDOW_WIDTH, JFRAME_WINDOW_HEIGHT);
        frame.setVisible(true);
    }

    private void initContainer(Container container, JPanel mapJPanel, JPanel addressJPanel) {
        container.add(BorderLayout.NORTH, mapJPanel);
        container.add(BorderLayout.CENTER, imageLabel);
        container.add(BorderLayout.SOUTH, addressJPanel);
    }

    private JPanel initMapJPanel(JLabel addressLbl, JButton button) {
        JPanel jPanel = new JPanel();
        jPanel.add(addressLbl);
        jPanel.add(address);
        jPanel.add(button);
        return jPanel;
    }

    private JPanel initAddressJPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(JFRAME_PRINT_ADDRESS_WIDTH, JFRAME_PRINT_ADDRESS_HEIGHT));
        panel.add(resAddress);
        panel.add(jibunAddress);
        panel.add(resX);
        panel.add(resY);
        return panel;
    }

    public static Address printAddressVo(JSONArray jsonArray) {
        Address addressResult = new Address();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            addressResult.setRoadAddress((String) object.get("roadAddress"));
            addressResult.setJibunAddress((String) object.get("jibunAddress"));
            addressResult.setX((String) object.get("x"));
            addressResult.setY((String) object.get("y"));
        }
        return addressResult;
    }

    private void initJLabel() {
        imageLabel = new JLabel(JFRAME_MAP_DESCRIPTION);
        resAddress = new JLabel(PRINT_ADDRESS_NAME);
        jibunAddress = new JLabel(PRINT_JIBUN_ADDRESS);
        resX = new JLabel(PRINT_LONGITUDE); // 경도
        resY = new JLabel(PRINT_LATITUDE); // 위도
    }

}
