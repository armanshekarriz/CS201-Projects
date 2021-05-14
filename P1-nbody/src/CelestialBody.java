

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;


	/*Constructor invoked by assigning variables data makes object variables equal to the variables.*/
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		this.myXPos = xp;
		this.myYPos = yp;
		this.myXVel = xv;
		this.myYVel = yv;
		this.myMass = mass;
		this.myFileName = filename;
	}

	/*The copy constructor uses accessor (.get) methods to get variable data and assign values to object variables.*/
	public CelestialBody(CelestialBody b){
		this.myXPos = b.getX();
		this.myYPos = b.getY();
		this.myXVel = b.getXVel();
		this.myYVel = b.getYVel();
		this.myMass = b.getMass();
		this.myFileName = b.getName();
	}

	/*This method returns X position*/
	public double getX() {
		return this.myXPos;
	}

	/*This method returns Y position*/
	public double getY() {
		return this.myYPos;
	}

	/*This method returns X velocity*/
	public double getXVel() {
		return this.myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */

	/*This method returns Y velocity*/
	public double getYVel() {
		return this.myYVel;
	}

	/*This method returns mass*/
	public double getMass() {
		return this.myMass;
	}

	/*This method returns File name*/
	public String getName() {
		return this.myFileName;
	}

	/*This method calculates distance to the other celestial body using math.sqrt and .get methods.*/
	public double calcDistance(CelestialBody b) {
		// TODO: complete method
		return (Math.sqrt(((b.getX()-this.getX())*(b.getX()-this.getX()))+((b.getY()-this.getY())*(b.getY()-this.getY()))));
	}

	/*This method calculates force from Newton's Law of Gravitation.*/
	public double calcForceExertedBy(CelestialBody b) {
		// TODO: complete method
		return ((6.67*1e-11)*this.getMass()*b.getMass()/(calcDistance(b)*calcDistance(b)));
	}

	/*This method calculates X-component of force.*/
	public double calcForceExertedByX(CelestialBody b) {
		// TODO: complete method
		return (calcForceExertedBy(b)*(b.getX()-this.getX())/calcDistance(b));
	}

	/*This method calculates Y-component of force.*/
	public double calcForceExertedByY(CelestialBody b) {
		// TODO: complete method
		return (calcForceExertedBy(b)*(b.getY()-this.getY())/calcDistance(b));
	}

	/*This method calculates net force on a body in the X direction.*/
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		// TODO: complete method
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if(! b.equals(this)) {
				sum = sum + calcForceExertedByX(b);
			}
		}
		return sum;
	}

	/*This method calculates net force on a body in the y direction.*/
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (! b.equals(this)) {
				sum = sum + calcForceExertedByY(b);
			}
		}
		return sum;
	}

	/*This method updates values of object variables after gravitation acts on them using equations of motion.*/
	public void update(double deltaT, 
			           double xforce, double yforce) {
		// TODO: complete method
		double ax = xforce/(this.myMass);
		double ay = yforce/(this.myMass);
		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
		return;
	}

	/*This method puts the image at given positions.*/
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
