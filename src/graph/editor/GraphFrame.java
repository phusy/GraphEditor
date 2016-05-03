package graph.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import automata.NotDeterministInitialStateException;
import automata.UnknownInitialStateException;
import automata.UnknownTerminalStateException;
import graph.editor.FileManager;

public class GraphFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GraphComponent component;
	private FramesController controller;

	public GraphFrame(FramesController controller) {
		this.controller = controller;

		component = new GraphComponent();
		component.setForeground(Color.BLACK);
		component.setBackground(Color.WHITE);
		component.setOpaque(true);
		component.setPreferredSize(new Dimension(1000, 1000));
		JScrollPane scrollPane = new JScrollPane(component);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(GraphEditor.MENU_FILE);
		menuBar.add(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_NEW, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.createFrame();
			}
		});
		createMenuItem(menu, GraphEditor.MENU_ITEM_CLOSE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_EXPORT, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				//GraphFrame.this.controller.createFrame();
				FileManager<GraphComponent> fileUtil = new FileManager<GraphComponent>();
				try {
					fileUtil.generateExport(component);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		createMenuItem(menu, GraphEditor.MENU_ITEM_IMPORT, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				FileManager<GraphComponent> fileUtil = new FileManager<GraphComponent>();
				try {
					GraphComponent temp = fileUtil.readExported();
					component.setVertices(temp.getVertices());
					component.setEdges(temp.getEdges());
					component.setColors(temp.getColors());
					repaint();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
		});
		
		createMenuSeparator(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_QUIT, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.quit();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});

		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new GridLayout(1,0));
		JButton b = addButton(toolbar, new Ellipse2D.Double(0, 0, 30, 30), 0, "Small Circle");
		b.doClick();
		addButton(toolbar, new Ellipse2D.Double(0, 0, 60, 60), 0, "Big Circle");
		addButton(toolbar, new Rectangle2D.Double(0, 0, 30, 30), 1, "Small Square");
		addButton(toolbar, new Rectangle2D.Double(0, 0, 60, 60), 1, "Big Square");
		addClearButton(toolbar);
		addCheckButton(toolbar);
		
		Container contentPane = getContentPane();
		contentPane.add(toolbar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	private JButton addButton(JToolBar toolbar, final RectangularShape sample, int form, String name) {
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				component.setShapeType(sample, form);
			}
		});
		toolbar.add(button);
		return button;
	}
	
	private JButton addClearButton(JToolBar toolbar) {
		JButton button = new JButton("Clear screen");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				component.clearList();
				repaint();
			}
		});		
		toolbar.add(button);
		return button;
	}
	
	private JButton addCheckButton(JToolBar toolbar) {
		JButton button = new JButton("Check string");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					component.checkString();
				} catch (UnknownInitialStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownTerminalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotDeterministInitialStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		toolbar.add(button);
		return button;
	}

	private void createMenuItem(JMenu menu, String name, ActionListener action) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(action);
		menu.add(menuItem);
	}

	private void createMenuSeparator(JMenu menu) {
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.lightGray);
		menu.add(separator);
	}
}
