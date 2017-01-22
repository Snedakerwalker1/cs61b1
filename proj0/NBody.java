public class NBody {
	public static double readRadius(String filename) {
		In in = new In(filename);
		in.readInt();
		return in.readDouble();
	}
	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int numPlanets = in.readInt();
		Planet[] planetArray = new Planet[numPlanets];
		in.readDouble();
		int i = 0;
		while (i < planetArray.length) {
			double tempXPos = in.readDouble();
			double tempYPos = in.readDouble();
			double tempXVel = in.readDouble();
			double tempYVel = in.readDouble();
			double tempMass = in.readDouble();
			String tempImg = in.readString();
			Planet tempPlanet = new Planet(tempXPos, tempYPos, tempXVel, tempYVel, tempMass, tempImg);
			planetArray[i] = tempPlanet;
			i += 1;
		}

		return planetArray;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		StdDraw.setXscale(-radius, radius);
		StdDraw.setYscale(-radius, radius);

		StdDraw.picture(0, 0, "images/starfield.jpg");

		/* Draws Planets */
		int i = 0;
		while (i < planets.length) {
			planets[i].draw();
			i += 1;
		}

		double time = 0;
		while (time < T) {
			StdDraw.picture(0, 0, "images/starfield.jpg");

			

			/* makes force arrays */
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			i = 0;
			while (i < planets.length) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				i += 1;
			}

			/* updates planets */
			i = 0;
			while (i < planets.length) {
				planets[i].update(dt, xForces[i], yForces[i]);
				i += 1;
			}

			/* Draws Planets again*/
			i = 0;
			while (i < planets.length) {
				planets[i].draw();
				i += 1;
			}
			
			StdDraw.show(10);

			time += dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
		}	
	}
}
