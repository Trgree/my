package com.ace.encry.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ace.encry.core.Encrypt;
import com.ace.encry.utils.Utils;

/**
 * 主窗口
 * 
 * @author Liangsj
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2746224826281328077L;
	private static final int HEIGHT = 450;
	private static final int WIDTH = 500;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JPasswordField keyField;
	private JButton encryptBtn;
	private JButton decryptBtn;
	private JButton clearBtn;
	private JButton copyBtn;
	private JLabel msgLabel;

	public MainFrame() {
		super();
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationCenter();
		setIcon();
		setTitle("加密/解密");
		setContentPane(getJContentPane());
	}

	private void setLocationCenter() {
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();// 获得显示器大小对象
		Dimension frameSize = getSize();// 获得窗口大小对象
		if (frameSize.width > displaySize.width)
			frameSize.width = displaySize.width;// 窗口的宽度不能大于显示器的宽度
		if (frameSize.height > displaySize.height)
			frameSize.height = displaySize.height;// 窗口的高度不能大于显示器的高度
		setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2);
	}

	private void setIcon(){
		URL url = this.getClass().getClassLoader().getResource("icon.png");
		Image image=Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(image);
	}

	private Container getJContentPane() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(getPanelNorth(), BorderLayout.NORTH);
		jPanel.add(getPanelCenter(), BorderLayout.CENTER);
		jPanel.add(getPanelBottom(), BorderLayout.SOUTH);
		jPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		return jPanel;
	}

	/** 密钥、消息 */
	private Component getPanelNorth() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(new JLabel("密钥:"));
		jPanel.add(getJPasswordField());
		jPanel.add(getMsgLabel());
		return jPanel;
	}

	private Component getJPasswordField() {
		if (keyField == null) {
			keyField = new JPasswordField(15);
			keyField.setEchoChar('*');
		}
		return keyField;
	}

	private Component getMsgLabel() {
		if (null == msgLabel) {
			msgLabel = new JLabel();
		}
		return msgLabel;
	}

	/** 输入输出框 */

	private Component getPanelCenter() {
		JPanel jPanel = new JPanel(new GridLayout(1, 2));
		jPanel.add(getPanelInput());
		jPanel.add(getPanelOutput());
		return jPanel;
	}

	private Component getPanelInput() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JLabel("输入:"), BorderLayout.NORTH);
		jPanel.add(getInputArea(), BorderLayout.CENTER);
		jPanel.add(getPanelClear(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getInputArea() {
		if (inputArea == null) {
			inputArea = new JTextArea();
			inputArea.setTabSize(4);
			inputArea.setRows(5);
			inputArea.setLineWrap(true);// 激活自动换行功能
			inputArea.setWrapStyleWord(true);// 激活断行不断字功能
			inputArea.setBackground(Color.white);
			inputArea.setAutoscrolls(true);
		}
		JScrollPane inputScroll = new JScrollPane(inputArea);
		return inputScroll;
	}

	private Component getPanelClear() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(getClearBtn(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getClearBtn() {
		if (null == clearBtn) {
			clearBtn = new JButton("清空");
			clearBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					inputArea.setText("");
					outputArea.setText("");
					inputArea.requestFocus();
				}
			});
		}
		return clearBtn;
	}

	private Component getPanelOutput() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JLabel("输出:"), BorderLayout.NORTH);
		jPanel.add(getJOutputArea(), BorderLayout.CENTER);
		jPanel.add(getPanelCopy(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getJOutputArea() {
		if (outputArea == null) {
			outputArea = new JTextArea();
			outputArea.setTabSize(4);
			outputArea.setRows(5);
			outputArea.setLineWrap(true);// 激活自动换行功能
			outputArea.setWrapStyleWord(true);// 激活断行不断字功能
			outputArea.setBackground(Color.white);
			outputArea.setEditable(false);
			outputArea.setAutoscrolls(true);
		}
		JScrollPane outputScroll = new JScrollPane(outputArea);
		return outputScroll;
	}

	private Component getPanelCopy() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(getCopyBtn(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getCopyBtn() {
		if (null == copyBtn) {
			copyBtn = new JButton("复制");
			copyBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Utils.copyText2Clipboard(outputArea.getText());
					msgLabel.setForeground(null);
					msgLabel.setText("已复制结果到剪贴版");
				}
			});
		}
		return copyBtn;
	}

	/** 底部按钮 */

	private Component getPanelBottom() {
		JPanel jPanel = new JPanel(new FlowLayout());
		jPanel.add(getEncryptBtn());
		jPanel.add(getDecryptBtn());
		return jPanel;
	}

	private Component getEncryptBtn() {
		if (encryptBtn == null) {
			encryptBtn = new JButton("加密");
			encryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					action(true);
				}
			});
		}
		return encryptBtn;
	}

	private Component getDecryptBtn() {
		if (decryptBtn == null) {
			decryptBtn = new JButton("解密");
			decryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					action(false);
				}
			});
		}
		return decryptBtn;
	}

	/**
	 * 加密解密事件
	 * 
	 * @param flag
	 *            为true时表示加密 false表示解密
	 */
	private void action(boolean flag) {
		String data = inputArea.getText().trim();
		String key = new String(keyField.getPassword()).trim();
		msgLabel.setText("");
		outputArea.setText("");
		msgLabel.setForeground(null);
		if ("".equals(data)) {
			msgLabel.setForeground(Color.red);
			msgLabel.setText("输入不能为空");
			return;
		}
		if ("".equals(key)) {
			msgLabel.setForeground(Color.red);
			msgLabel.setText("密钥不能为空");
			return;
		}

		String result = null;
		/** 加密 */
		if (flag) {
			try {
				result =Encrypt.encrypt(data, key);
			} catch (Exception e) {
				msgLabel.setForeground(Color.red);
				msgLabel.setText("加密失败");
				return;
			}
		} else {
			/** 解密 */
			try {
				result =Encrypt.decrypt(data, key);
			} catch (Exception e) {
				msgLabel.setForeground(Color.red);
				msgLabel.setText("解密失败，请确认密钥是否正确");
				return;
			}
		}
		outputArea.setText(result);
	}
}
