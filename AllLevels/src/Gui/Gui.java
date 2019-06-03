package Gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class Gui {

	private JFrame fram;
	private JTextField TextAngle;
	private JTextField TextRed;
	private JTextField TextBlue;
	private JTextField TextGreen;
	private JTextField FileNameText;

	/**
	 * Launch the application.
	 */
	public void swap(Point3D p) {
		double x = p.getX().getCoordinate();
		double y = p.getY().getCoordinate();
		p.setX(new Coordinate(y));
		p.setY(new Coordinate(x));
	}
	
	public Point3D AngleToVector() throws Exception
	{
		int angle = Integer.parseInt(TextAngle.getText());
		if (angle>180 || angle < 0) 
			throw new Exception("Invalid Input");
        double X,Y;
        
        Y=500*Math.sin(Math.toRadians(angle));
        X=-500*Math.cos(Math.toRadians(angle));
        
        return new Point3D(Y, X, 0);
	}

	public void growthZ(Point3D p) {
		p.setZ(p.getZ().subtract(new Coordinate(50)));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.fram.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fram = new JFrame();
		fram.setTitle("Picture Builder");
		fram.getContentPane().setBackground(new Color(255, 255, 204));
		fram.getContentPane().setForeground(Color.BLACK);
		fram.setBounds(200, 500, 708, 550);
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fram.setLocation(0, 0);
		fram.getContentPane().setLayout(null);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 398, 692, 14);
		fram.getContentPane().add(progressBar);

		JLabel lblNewLabel = new JLabel("Picture Builder");
		lblNewLabel.setForeground(new Color(0, 128, 128));
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 38));
		lblNewLabel.setBounds(169, 31, 343, 46);
		fram.getContentPane().add(lblNewLabel);

		JRadioButton SLRB = new JRadioButton("Spot Light");

		SLRB.setBackground(new Color(255, 255, 204));
		SLRB.setFont(new Font("Blackadder ITC", Font.PLAIN, 26));
		JRadioButton PLRB = new JRadioButton("Point Light");

		PLRB.setBackground(new Color(255, 255, 204));
		PLRB.setFont(new Font("Blackadder ITC", Font.PLAIN, 28));
		JRadioButton DLRB = new JRadioButton("Directional Light");

		DLRB.setBackground(new Color(255, 255, 204));
		DLRB.setFont(new Font("Blackadder ITC", Font.PLAIN, 28));

		DLRB.setBounds(29, 84, 230, 44);
		PLRB.setBounds(281, 84, 156, 44);
		SLRB.setBounds(496, 85, 172, 44);

		ButtonGroup group = new ButtonGroup();
		group.add(DLRB);
		group.add(PLRB);
		group.add(SLRB);

		fram.getContentPane().add(DLRB);
		fram.getContentPane().add(PLRB);
		fram.getContentPane().add(SLRB);

//		JLabel lightDetails = new JLabel ("Light details:");
//		lightDetails.setFont(new Font("Algerian", Font.PLAIN, 22));
//		lightDetails.setBounds(100, 80, 50, 40);
//		fram.getContentPane().add(lightDetails);
		
		JLabel lblNewLabel_1 = new JLabel("Light Angle(0-180):");
		lblNewLabel_1.setFont(new Font("Algerian", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(30, 152, 115, 40);
		fram.getContentPane().add(lblNewLabel_1);

		JLabel lblColor = new JLabel("Light Color:");
		lblColor.setFont(new Font("Algerian", Font.PLAIN, 16));
		lblColor.setBounds(380, 157, 69, 30);
		fram.getContentPane().add(lblColor);

		TextAngle = new JTextField();
		TextAngle.setBounds(184, 163, 86, 20);
		fram.getContentPane().add(TextAngle);
		TextAngle.setColumns(10);

		TextRed = new JTextField();
		TextRed.setBounds(447, 163, 50, 20);
		fram.getContentPane().add(TextRed);
		TextRed.setColumns(10);
//***************************************************     RECURSIVE    ****************//
		JButton ButtonRecursive = new JButton("Recursive Test");
		ButtonRecursive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Message Box",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					Scene scene = new Scene();
					scene.setScreenDistance(300);

					Sphere sphere = new Sphere(250, new Point3D(0, 0, -1000));
					sphere.setShininess(20);
					sphere.setEmmission(new Color(0, 0, 100));
					sphere.getMaterial().setKt(0.3);
					scene.addGeometry(sphere);

					Sphere sphere2 = new Sphere(150, new Point3D(0, 0, -1000));
					sphere2.setShininess(20);
					sphere2.setEmmission(new Color(100, 20, 20));
					sphere2.getMaterial().setKt(0.7);
					scene.addGeometry(sphere2);

					Triangle triangle = new Triangle(new Point3D(2000, -1000, -1500), 
							 						 new Point3D(-1000, 2000, -1500),
							 						 new Point3D(700, 700, -375));

					Triangle triangle2 = new Triangle(new Point3D(2000, -1000, -1500), 
							 	 				      new Point3D(-1000, 2000, -1500),
							 	 				      new Point3D(-1000, -1000, -1500));

					triangle.setEmmission(new Color(20, 20, 20));
					triangle2.setEmmission(new Color(20, 20, 20));
					triangle.getMaterial().setKr(1);
					triangle2.getMaterial().setKr(0.5);
					scene.addGeometry(triangle);
					scene.addGeometry(triangle2);

					int r = Integer.parseInt(TextRed.getText());
					int g = Integer.parseInt(TextGreen.getText());
					int b = Integer.parseInt(TextBlue.getText());
					try {
							if (r>255 || r < 0 || b>255 || b < 0 || g>255 || g < 0 )
								throw new Exception("Invalid Input");
						} catch (Exception e2) {
							e2.printStackTrace();
						}

					try {
						if (SLRB.isSelected()) {
							Point3D p = AngleToVector();
							scene.addLight(new SpotLight(new Color(r, g, b),
									p,new Vector(scene.getCamera().getP0(),p), 0, 0.00001, 0.0000005));

						}
						if (PLRB.isSelected()) {
							scene.addLight(new PointLight(new Color(r, g, b), 
											AngleToVector(),
											0, 0.000001,0.0000005));

						}

						if (DLRB.isSelected()) {
							//Vector v = new Vector();
							scene.addLight(new DirectionalLight(new Color(r, g, b), new Vector(sphere.getCenter() ,AngleToVector())));

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					String name = FileNameText.getText();
					ImageWriter imageWriter = new ImageWriter(name, 500, 500, 500, 500);

					Render render = new Render(imageWriter, scene);

					try {
						render.renderImage();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					render.writeToImage();
					
					try {
						String path = render.getImageWriter().getRenderingDirectory(); //System.getProperty("user.dir") + "\\Try";
						String jpg = ".jpg";
						Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileNameText.getText() +jpg);
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		ButtonRecursive.setFont(new Font("Segoe Script", Font.BOLD, 18));
		ButtonRecursive.setBounds(28, 233, 218, 46);
		fram.getContentPane().add(ButtonRecursive);

		JButton ButtonShadow = new JButton("Shadow Test");
		ButtonShadow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Message Box",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					Scene scene = new Scene();
					Sphere sphere = new Sphere(500, new Point3D(0.0, 0.0, -1000));
					sphere.setShininess(20);
					sphere.setEmmission(new Color(0, 0, 100));

					scene.addGeometry(sphere);

					Triangle triangle1 = new Triangle(new Point3D(3500, 3500, -2000), new Point3D(-3500, -3500, -1000),
							new Point3D(3500, -3500, -2000));

					Triangle triangle2 = new Triangle(new Point3D(3500, 3500, -2000), new Point3D(-3500, 3500, -1000),
							new Point3D(-3500, -3500, -1000));
					// triangle1.getMaterial().set_Ks(0);
					// triangle2.getMaterial().set_Ks(0);
					scene.addGeometry(triangle1);

					scene.addGeometry(triangle2);

					int r = Integer.parseInt(TextRed.getText());
					int g = Integer.parseInt(TextGreen.getText());
					int b = Integer.parseInt(TextBlue.getText());
					try {
						if (r>255 || r < 0 || b>255 || b < 0 || g>255 || g < 0 )
							throw new Exception("Invalid Input");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
					try {
						if (SLRB.isSelected()) {
							scene.addLight(new SpotLight(new Color(r, g, b),
									AngleToVector(),new Vector(AngleToVector(), sphere.getCenter()) ,0, 0.000001, 0.0000005));

						}
						if (PLRB.isSelected()) {
							scene.addLight(new PointLight(new Color(r, g, b), AngleToVector(), 0, 0.000001,
									0.0000005));

						}

						if (DLRB.isSelected()) {
							Vector v = new Vector();
							scene.addLight(new DirectionalLight(new Color(r, g, b), new Vector(sphere.getCenter() ,AngleToVector())));

						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ImageWriter imageWriter = new ImageWriter(FileNameText.getText(), 500, 500, 500, 500);

					Render render = new Render(imageWriter, scene);

					try {
						render.renderImage();
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					render.writeToImage();

					try {
						String path = render.getImageWriter().getRenderingDirectory(); //System.getProperty("user.dir") + "\\Try";
						String jpg = ".jpg";
						Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileNameText.getText() +jpg);
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		ButtonShadow.setFont(new Font("Segoe Script", Font.BOLD, 18));
		ButtonShadow.setBounds(28, 286, 218, 44);
		fram.getContentPane().add(ButtonShadow);

		JButton ButtonTigris = new JButton("Tigris Test");
		ButtonTigris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Message Box",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					Scene scene = new Scene();
					scene.setScreenDistance(50);

					Point3D[] p = { new Point3D(0, 0, 0), new Point3D(-288, 75, 0.000010),
							new Point3D(178, 85, 0.000012), new Point3D(-358, -33, -0.000005),
							new Point3D(-58, 44, 0.000006), new Point3D(43, 4, 0.000001),
							new Point3D(-191, -57, -0.000008), new Point3D(169, -8, -0.000001),
							new Point3D(-12, -56, -0.000008), new Point3D(73, -61, -0.000008),
							new Point3D(131, -6, -0.000001), new Point3D(157, 40, 0.000005),
							new Point3D(356, -17, -0.000002), new Point3D(367, -56, -0.000008),
							new Point3D(383, -18, -0.000003), new Point3D(374, -80, -0.000011),
							new Point3D(400, -56, -0.000008), new Point3D(318, -44, -0.000006),
							new Point3D(275, -34, -0.000005), new Point3D(207, -17, -0.000002),
							new Point3D(279, -83, -0.000011), new Point3D(238, -55, -0.000007),
							new Point3D(228, -142, -0.000019), new Point3D(258, -135, -0.000018),
							new Point3D(234, -84, -0.000011), new Point3D(209, -115, -0.000016),
							new Point3D(278, 40., 0.000005), new Point3D(347, 116, 0.000016),
							new Point3D(387, 164, 0.000022), new Point3D(153, 146, 0.000020),
							new Point3D(213, 205, 0.000028), new Point3D(96, 108, 0.000015),
							new Point3D(5, 140, 0.000019), new Point3D(-159, 103, 0.000014),
							new Point3D(-444, 123, 0.000017), new Point3D(-255, 110, 0.000015),
							new Point3D(-537, 198, 0.000027), new Point3D(-614, 186, 0.000025),
							new Point3D(-529, 154, 0.000021), new Point3D(-665, 144, 0.000019),
							new Point3D(-612, 148, 0.000020), new Point3D(-668, 92, 0.000012),
							new Point3D(-254, -95, -0.000013), new Point3D(-434, -126, -0.000017),
							new Point3D(-330, -128, -0.000017), new Point3D(-476, -92, -0.000012),
							new Point3D(-469, -60, -0.000008), new Point3D(-439, -228, -0.000031),
							new Point3D(-437, -177, -0.000024), new Point3D(-393, -212, -0.000029),
							new Point3D(-480, -193, -0.000026), new Point3D(395, 128, 0.000017),
							new Point3D(428, 152, 0.000020), new Point3D(432, 190, 0.000026),
							new Point3D(475, 96, 0.000013), new Point3D(478, 118, 0.000016),
							new Point3D(430, 171, 0.000023), new Point3D(449, 150, 0.000020),
							new Point3D(472, 185, 0.000025), new Point3D(470, 44, 0.000006),
							new Point3D(495, 66, 0.000009), new Point3D(388, 54, 0.000007),
							new Point3D(429, 49, 0.000007), new Point3D(452, 25, 0.000003),
							new Point3D(341, 97, 0.000013), new Point3D(309, 68, 0.000009),
							new Point3D(375, 66, 0.000009)

					};

					Triangle[] shapes = {

							new Triangle(p[31], p[4], p[2]), new Triangle(p[3], p[4], p[1]),
							new Triangle(p[4], p[5], p[2]), new Triangle(p[5], p[4], p[6]),
							new Triangle(p[8], p[9], p[7]), new Triangle(p[10], p[8], p[7]),
							new Triangle(p[6], p[8], p[5]), new Triangle(p[7], p[11], p[10]),
							new Triangle(p[11], p[7], p[12]), new Triangle(p[12], p[18], p[17]),
							new Triangle(p[13], p[14], p[12]), new Triangle(p[13], p[16], p[14]),
							new Triangle(p[13], p[17], p[15]), new Triangle(p[18], p[19], p[20]),
							new Triangle(p[19], p[21], p[20]), new Triangle(p[20], p[24], p[22]),
							new Triangle(p[22], p[23], p[20]), new Triangle(p[22], p[24], p[25]),
							new Triangle(p[12], p[26], p[11]), new Triangle(p[27], p[65], p[64]),
							new Triangle(p[26], p[2], p[11]), new Triangle(p[5], p[11], p[2]),
							new Triangle(p[28], p[29], p[27]), new Triangle(p[29], p[28], p[30]),
							new Triangle(p[2], p[29], p[31]), new Triangle(p[32], p[29], p[30]),
							new Triangle(p[31], p[33], p[1]), new Triangle(p[35], p[1], p[33]),
							new Triangle(p[34], p[35], p[36]), new Triangle(p[36], p[38], p[34]),
							new Triangle(p[37], p[40], p[38]), new Triangle(p[39], p[41], p[40]),
							new Triangle(p[3], p[6], p[4]), new Triangle(p[42], p[3], p[43]),
							new Triangle(p[43], p[44], p[42]), new Triangle(p[43], p[3], p[45]),
							new Triangle(p[45], p[3], p[46]), new Triangle(p[45], p[48], p[43]),
							new Triangle(p[47], p[49], p[48]), new Triangle(p[47], p[45], p[50]),
							new Triangle(p[28], p[27], p[51]), new Triangle(p[51], p[52], p[28]),
							new Triangle(p[56], p[28], p[52]), new Triangle(p[57], p[58], p[56]),
							new Triangle(p[55], p[57], p[54]), new Triangle(p[52], p[57], p[56]),
							new Triangle(p[59], p[60], p[54]), new Triangle(p[51], p[54], p[52]),
							new Triangle(p[51], p[62], p[59]), new Triangle(p[62], p[63], p[59]),
							new Triangle(p[66], p[27], p[64]), new Triangle(p[64], p[65], p[66]),
							new Triangle(p[31], p[1], p[4]), new Triangle(p[66], p[61], p[51]),
							new Triangle(p[2], p[26], p[65]), new Triangle(p[10], p[5], p[8]),
							new Triangle(p[12], p[7], p[19]), new Triangle(p[19], p[18], p[12]),
							new Triangle(p[17], p[13], p[12]), new Triangle(p[13], p[15], p[16]),
							new Triangle(p[19], p[7], p[21]), new Triangle(p[20], p[21], p[24]),
							new Triangle(p[27], p[2], p[65]), new Triangle(p[5], p[10], p[11]),
							new Triangle(p[2], p[27], p[29]), new Triangle(p[32], p[31], p[29]),
							new Triangle(p[31], p[32], p[33]), new Triangle(p[35], p[34], p[1]),
							new Triangle(p[36], p[37], p[38]), new Triangle(p[37], p[39], p[40]),
							new Triangle(p[3], p[42], p[6]), new Triangle(p[45], p[47], p[48]),
							new Triangle(p[56], p[53], p[28]), new Triangle(p[52], p[54], p[57]),
							new Triangle(p[51], p[59], p[54]), new Triangle(p[51], p[61], p[62]),
							new Triangle(p[66], p[51], p[27]), };

					for (int i = 0; i < shapes.length; i++) {
						Triangle tmp1 = shapes[i];
						Point3D tmp2 = tmp1.getP1();
						Point3D tmp3 = tmp1.getP2();
						Point3D tmp4 = tmp1.getP3();
						swap(tmp2);
						swap(tmp3);
						swap(tmp4);
						growthZ(tmp2);
						growthZ(tmp3);
						growthZ(tmp4);
					}

//					Color[] colors = { new Color(32, 178, 170), new Color(60, 179, 119), new Color(70, 130, 180),
//							new Color(70, 130, 180), new Color(34, 140, 34), new Color(0, 255, 0),
//							new Color(255, 255, 0), new Color(107, 142, 35), new Color(0, 0, 128),
//							new Color(100, 144, 237), new Color(0, 191, 255), new Color(32, 178, 170) };

					for (int i = 0; i < shapes.length; i++) {
						// shapes[i].setEmission(colors[(i%12)]);
						shapes[i].setEmmission(new Color(i * 30 % 255, i * 40 % 255, i * 20 % 255));
						scene.addGeometry(shapes[i]);
					}

					int r = Integer.parseInt(TextRed.getText());
					int g = Integer.parseInt(TextGreen.getText());
					int b = Integer.parseInt(TextBlue.getText());
					try {
						if (r>255 || r < 0 || b>255 || b < 0 || g>255 || g < 0 )
							throw new Exception("Invalid Input");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
					try {
						if (SLRB.isSelected()) {
							scene.addLight(new SpotLight(new Color(r, g, b), 
									AngleToVector(),new Vector(shapes[40].getP1() ,AngleToVector()) ,0, 0.000001, 0.0000005));

						}
						if (PLRB.isSelected()) {
							scene.addLight(new PointLight(new Color(r, g, b), new Point3D(200, 200, -100), 0, 0.000001,
									0.0000005));

						}

						if (DLRB.isSelected()) {
							Point3D pnt = new Point3D(Math.cos(Integer.parseInt(TextAngle.getText())),
									Math.sin(Integer.parseInt(TextAngle.getText())), 0);
							Vector v = new Vector(pnt);
							scene.addLight(new DirectionalLight(new Color(r, g, b), new Vector(shapes[40].getP1() ,AngleToVector())));

							
								
						}
						// prompt no light source chosen
						else if(!SLRB.isSelected() && !PLRB.isSelected() && !DLRB.isSelected())
						{
							
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					ImageWriter imageWriter = new ImageWriter("Tigris test1", 1300, 1300, 1300, 1300);

					Render render = new Render(imageWriter, scene);

					try {
						render.renderImage();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					// render.printGrid(25);
					render.writeToImage();
					
					try {
						String path = render.getImageWriter().getRenderingDirectory(); //System.getProperty("user.dir") + "\\Try";
						String jpg = ".jpg";
						Runtime.getRuntime().exec("explorer.exe /open, " + path+"\\" + FileNameText.getText() +jpg);
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		ButtonTigris.setFont(new Font("Segoe Script", Font.BOLD, 18));

		ButtonTigris.setBounds(28, 339, 218, 46);
		fram.getContentPane().add(ButtonTigris);

		TextBlue = new JTextField();
		TextBlue.setBounds(587, 163, 50, 20);
		fram.getContentPane().add(TextBlue);
		TextBlue.setColumns(10);

		TextGreen = new JTextField();
		TextGreen.setBounds(515, 163, 50, 20);
		fram.getContentPane().add(TextGreen);
		TextGreen.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Red");
		lblNewLabel_2.setFont(new Font("MV Boli", Font.PLAIN, 26));
		lblNewLabel_2.setBounds(436, 196, 55, 30);
		fram.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Green");
		lblNewLabel_3.setFont(new Font("MV Boli", Font.PLAIN, 26));
		lblNewLabel_3.setBounds(506, 194, 83, 34);
		fram.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Blue");
		lblNewLabel_4.setFont(new Font("MV Boli", Font.PLAIN, 26));
		lblNewLabel_4.setBounds(587, 198, 58, 26);
		fram.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("File name");
		lblNewLabel_5.setFont(new Font("Script MT Bold", Font.PLAIN, 26));
		lblNewLabel_5.setBounds(356, 254, 115, 30);
		fram.getContentPane().add(lblNewLabel_5);

		FileNameText = new JTextField();
		FileNameText.setBounds(332, 294, 156, 30);
		fram.getContentPane().add(FileNameText);
		FileNameText.setColumns(10);

		JButton ButtonExit = new JButton("Exit");
		ButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ButtonExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fram.dispose();
			}

		});
		ButtonExit.setFont(new Font("Algerian", Font.PLAIN, 24));
		ButtonExit.setBounds(537, 336, 131, 46);
		fram.getContentPane().add(ButtonExit);

		 DLRB.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent arg0) {
		 }
		 });
	}
}
