package com.expreiment02.Calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	static JPanel centerPanel = new JPanel();// 创建一个面板对象
	static JTextField tfAnswer = new JTextField("");// 创建文本框
	static JButton[] b = new JButton[10];// 声明数字按钮数组
	static JButton bp, ba, bs, bm, bd, be, left, right, ac, back;// 声明操作按钮对象

	public Calculator() {
		
		setLocation(200, 200);// 设置窗口生成的相对位置

		for (int i = 0; i <= 9; i++) {
			b[i] = new JButton("" + i); // 创建数字按钮对象
			b[i].setBackground(Color.white);// 设置数字按钮颜色

		}

		// 创建操作按钮对象
		left = new JButton("(");
		right = new JButton(")");
		ac = new JButton("清屏");
		back = new JButton("退格");
		bp = new JButton(".");
		ba = new JButton("+");
		bs = new JButton("-");
		bm = new JButton("*");
		bd = new JButton("/");
		be = new JButton("=");

		// 设置按钮颜色
		be.setBackground(Color.white);
		bp.setBackground(Color.white);

		setTitle("计算器");// 设置窗口标题
		setLayout(null);// 取消窗口的布局管理器
		setSize(450, 400);// 设置窗口的大小
		setResizable(false);// 设置窗口的大小为不可改变
		GridLayout grid = new GridLayout(5, 4);// 创建5行4列的页面布局
		centerPanel.setLayout(grid);// 将面板对象centerPanel的布局策略设为网格布局方式
		tfAnswer.setBounds(35, 15, 248, 60);// 设置文本框tfAnswer相对窗口的位置及大小
		tfAnswer.setEditable(false);// 设置文本框为不可编辑的
		centerPanel.setBounds(35, 100, 250, 200);// 设置面板相对窗口的位置和大小
		tfAnswer.setBackground(Color.lightGray);// 设置文本框的背景色
		tfAnswer.setHorizontalAlignment(JTextField.RIGHT);// 设置文本框的文字右对齐
		Font font = new Font("黑体", Font.PLAIN, 20);// 创建字体
		tfAnswer.setFont(font);// 设置文本框字体
		
		// 添加按钮到面板
		centerPanel.add(left);
		centerPanel.add(right);
		centerPanel.add(ac);
		centerPanel.add(back);
		centerPanel.add(b[7]);
		centerPanel.add(b[8]);
		centerPanel.add(b[9]);
		centerPanel.add(bd);
		centerPanel.add(b[4]);
		centerPanel.add(b[5]);
		centerPanel.add(b[6]);
		centerPanel.add(bm);
		centerPanel.add(b[1]);
		centerPanel.add(b[2]);
		centerPanel.add(b[3]);
		centerPanel.add(bs);
		centerPanel.add(b[0]);
		centerPanel.add(bp);
		centerPanel.add(be);
		centerPanel.add(ba);
		
		// 设置按钮的监听者是本窗口
		left.addActionListener(this);
		right.addActionListener(this);
		ac.addActionListener(this);
		back.addActionListener(this);
		bp.addActionListener(this);
		ba.addActionListener(this);
		bm.addActionListener(this);
		bd.addActionListener(this);
		be.addActionListener(this);
		bs.addActionListener(this);
		
		for (int i = 0; i < 10; i++) // 为数字按钮注册监听者
			b[i].addActionListener(this);
		
		add(tfAnswer);// 添加文本框到窗口
		add(centerPanel);// 添加面板到窗口
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 处理关闭窗口事件
		setVisible(true);// 设置窗口为可见
	}

	public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		if (bt.getText().equals("清屏"))// 如果清屏按钮被点击
		{
			tfAnswer.setText("");
		} else if (bt.getText().equals("退格"))// 如果退格按钮被点击
		{
			char[] str = tfAnswer.getText().toCharArray();
			if (str.length > 0)
				tfAnswer.setText(new String(str, 0, str.length - 1));
		} else if (bt.getText().equals("="))// 如果“=”按钮被点击
		{
			tfAnswer.setText(tfAnswer.getText());
			char[] str = tfAnswer.getText().toCharArray();
			String ss = new Calculator01().cal_string(str);// 调用匿名对象Calculator的方法计算结果
			tfAnswer.setText(ss);
		} else {
			tfAnswer.setText(tfAnswer.getText() + bt.getText());
		}
	}

	public static void main(String[] args) {
		new Calculator();
	}
}

//计算一个字符串对应的值的类
class Calculator01 {
	int[] cmp = new int[500];

	Calculator01()// 初始化每个符号对应的大小，方便比较优先级
	{
		cmp['+'] = cmp['-'] = 1;
		cmp['*'] = cmp['/'] = 2;
	}

	private double calculate(double t, double t1, double t2)
	// 进行加减乘除的运算
	{
		if (t == '+')
			return t1 + t2;
		if (t == '-')
			return t1 - t2;
		if (t == '*')
			return t1 * t2;
		return t1 / t2;
	}

	public String cal_string(char[] s) {
		/*
		 * 计算字符串结果的方法 首先计算对应字符串的后缀表达式，然后从左向右扫描一下后缀表达式即可得到结果
		 */
		int i = 0, len = s.length;
		double[] c = new double[1000];
		// c为符号栈
		int[] vis = new int[1000];// 判断该位是不是符号
		for (int j = 0; j < 1000; j++)
			vis[j] = 0;
		double[] suffix = new double[1000];// 存储后缀表达式的栈
		int top = 0, tops = 0;

		while (i < len) {
			if (s[i] >= '0' && s[i] <= '9' || s[i] == '.') {
				String str = "" + s[i++];
				while (i < len && (s[i] >= '0' && s[i] <= '9' || s[i] == '.'))
					str += s[i++];
				suffix[tops++] = Double.parseDouble(str);
			} else {
				switch (s[i]) {
				case '(':
					c[top++] = s[i];
					break;
				case ')':
					while (top > 0 && c[top - 1] != '(') {
						vis[tops] = 1;
						suffix[tops++] = c[--top];
					}
					top--;
					break;
				default:
					while (top > 0 && c[top - 1] != '(' && cmp[(int) (c[top - 1] + 0.1)] >= cmp[s[i]]) {
						vis[tops] = 1;
						suffix[tops++] = c[--top];
					}
					c[top++] = s[i];
				}
				i++;
			}
		}
		while (top > 0) {
			vis[tops] = 1;
			suffix[tops++] = c[--top];
		}
		// 此时后缀表达式已找到,再临时利用一个栈即可算出结果
		for (int j = 0; j < tops; j++) {
			if (vis[j] == 0)
				c[top++] = suffix[j];
			else {
				c[top - 2] = calculate(suffix[j], c[top - 2], c[top - 1]);
				top--;
			}
		}

		String ss = Double.toString(c[0]);

		return ss;
	}
}
