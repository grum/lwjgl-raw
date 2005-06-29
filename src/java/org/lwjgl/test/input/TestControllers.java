package org.lwjgl.test.input;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

/**
 * Oops. Forgot to document this one.
 * 
 * @author Kevin Glass
 */
public class TestControllers extends JPanel {
	public static int total = 0;
	
	private JTextField[] values;
	private JTextField[] names;
	private Controller controller;
	private int buttonCount;
	private int itemCount;
	
	public TestControllers(int index) {
		controller = Controllers.getController(index);
		setLayout(null);
		
		buttonCount = controller.getButtonCount();
		itemCount = controller.getButtonCount() + controller.getAxisCount() + 2;
		values = new JTextField[itemCount];
		names = new JTextField[itemCount];
		
		for (int i=0;i<controller.getButtonCount();i++) {
			names[i] = new JTextField();
			names[i].setEditable(false);
			names[i].setBounds(0,i*30,100,30);
			names[i].setText(controller.getButtonName(i));
			add(names[i]);
			values[i] = new JTextField();
			values[i].setEditable(false);
			values[i].setBounds(100,i*30,100,30);
			add(values[i]);
		}
		for (int i=buttonCount;i<buttonCount+controller.getAxisCount();i++) {
			names[i] = new JTextField();
			names[i].setEditable(false);
			names[i].setBounds(0,i*30,100,30);
			names[i].setText(controller.getAxisName(i-buttonCount));
			add(names[i]);
			values[i] = new JTextField();
			values[i].setEditable(false);
			values[i].setBounds(100,i*30,100,30);
			add(values[i]);
		}
		
		int i = itemCount - 2;
		names[i] = new JTextField();
		names[i].setEditable(false);
		names[i].setBounds(0,i*30,100,30);
		names[i].setText("POV X");
		add(names[i]);
		values[i] = new JTextField();
		values[i].setEditable(false);
		values[i].setBounds(100,i*30,100,30);
		add(values[i]);

		i = itemCount - 1;
		names[i] = new JTextField();
		names[i].setEditable(false);
		names[i].setBounds(0,i*30,100,30);
		names[i].setText("POV Y");
		add(names[i]);
		values[i] = new JTextField();
		values[i].setEditable(false);
		values[i].setBounds(100,i*30,100,30);
		add(values[i]);
		
		total++;
		
		setPreferredSize(new Dimension(200,30*itemCount));
		JFrame frame = new JFrame(controller.getName());
		frame.setContentPane(new JScrollPane(this));
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				total--;
				if (total == 0) {
					System.exit(0);
				}
			}
		});
		frame.setSize(230,400);
		frame.setLocation(index*30,index*30);
		frame.setVisible(true);
		
		Thread t = new Thread() {
			public void run() {
				while (true) {
					try { Thread.sleep(100); } catch (Exception e) {};
					pollAndUpdate();
				}
			}
		};
		t.start();
	}
	
	public void pollAndUpdate() {
		Controllers.poll();
		
		for (int i=0;i<controller.getButtonCount();i++) {
			values[i].setText(""+controller.isButtonPressed(i));
		}
		for (int i=buttonCount;i<buttonCount+controller.getAxisCount();i++) {
			values[i].setText(""+controller.getAxisValue(i-buttonCount));
		}
		
		values[itemCount-2].setText(""+controller.getPovX());
		values[itemCount-1].setText(""+controller.getPovY());
		
		while (Controllers.next()) {
			System.out.println("Event Fired: ");
			System.out.println("\t"+Controllers.getEventSource()+":"+Controllers.getEventControlIndex()+":"+Controllers.isEventButton());
			System.out.println("\t"+Controllers.isEventXAxis()+":"+Controllers.isEventYAxis());
		}
	}
	
	public static void main(String[] argv) {
		try {
			Controllers.create();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		int count = Controllers.getControllerCount();
		System.out.println(count+" Controllers Found");
		for (int i=0;i<count;i++) {
			Controller controller = Controllers.getController(i);
			System.out.println(controller.getName());
		}
		
		if (count == 0) {
			System.exit(0);
		}
		
		for (int i=0;i<count;i++) {
			new TestControllers(i);
		}
	}
}
