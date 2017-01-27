public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, 
					double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet P) {
		xxPos = P.xxPos;
		yyPos = P.yyPos;
		xxVel = P.xxVel;
		yyVel = P.yyVel;
		mass = P.mass;
		imgFileName = P.imgFileName;
	}

	public double calcDistance(Planet P) {
		double xDistance = this.xxPos - P.xxPos;
		double yDistance = this.yyPos - P.yyPos;
		return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
	}

	public double calcForceExertedBy(Planet P) {
		double G = 6.67e-11;
		return G * this.mass * P.mass / (this.calcDistance(P) * this.calcDistance(P));
	}

	public double calcForceExertedByX(Planet P) {
		double xDistance = P.xxPos - this.xxPos;
		return this.calcForceExertedBy(P) * xDistance / this.calcDistance(P);
	}

	public double calcForceExertedByY(Planet P) {
		double yDistance = P.yyPos - this.yyPos;
		return this.calcForceExertedBy(P) * yDistance / this.calcDistance(P);
	}

	public double calcNetForceExertedByX(Planet[] pList) {
		int i = 0;
		double netForce = 0;
		while (i < pList.length) {
			if (this != pList[i]){
				netForce += this.calcForceExertedByX(pList[i]);
			}
		i += 1;
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Planet[] pList) {
		int i = 0;
		double netForce = 0;
		while (i < pList.length) {
			if (this != pList[i]){
				netForce += this.calcForceExertedByY(pList[i]);
			}
		i += 1;
		}
		return netForce;
	}

	public void update(double dt, double xForce, double yForce) {
		double xAcceleration = xForce / this.mass;
		double yAcceleration = yForce / this.mass;
		this.xxVel = this.xxVel + xAcceleration * dt;
		this.yyVel = this.yyVel + yAcceleration * dt;
		this.xxPos = this.xxPos + this.xxVel * dt;
		this.yyPos = this.yyPos + this.yyVel * dt; 
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}
