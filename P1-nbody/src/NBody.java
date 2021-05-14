/**
 * @author Arman Shekarriz
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NBody {

	/*This method reads radius of universe by using values from the data file.*/
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		int num = s.nextInt();
		double rad = s.nextDouble();
		s.close();
		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	/*This method take variable data using a scanner and assigns it an object which it put into a new array of bodies.*/
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fname));
		int nb = s.nextInt(); // # bodies to be read
		double radi = s.nextDouble();
		ArrayList<CelestialBody> t = new ArrayList<>();
		for(int k=0; k < nb; k++) {
			double xpos = s.nextDouble();
			double ypos = s.nextDouble();
			double xvel = s.nextDouble();
			double yvel = s.nextDouble();
			double mas = s.nextDouble();
			String files = s.next();
			CelestialBody a = new CelestialBody(xpos, ypos, xvel, yvel, mas, files);
			t.add(a);
		}
		CelestialBody[] bod = t.toArray(new CelestialBody[nb]);
		s.close();
		return bod;
	}

	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;

		String fname= "./data/kaleidoscope.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");

		// TODO: for music/sound, uncomment next line

		StdAudio.play("images/2001.wav");

		// run simulation until over
		/*This stores x and y direction force for each of the objects in an array.*/
		for(double t = 0.0; t < totalTime; t += dt) {
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];

			/*This finds values of x and y direction forces and stores them.*/
			for(int k=0; k < bodies.length; k++) {
				xforces[k] = bodies[k].calcNetForceExertedByX(bodies);
				yforces[k] = bodies[k].calcNetForceExertedByY(bodies);
  			}

			/*This updates values of variables for each planet based on computed forces.*/
			for(int k=0; k < bodies.length; k++){
				bodies[k].update(dt, xforces[k], yforces[k]);
			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			}

			/*This draws the planets in their current position and as they move.*/
			for(CelestialBody b : bodies){
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
