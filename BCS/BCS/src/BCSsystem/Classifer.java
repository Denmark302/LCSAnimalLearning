package BCSsystem;

public class Classifer extends Scenario{

public float PayoffExpected;
public String Action;

public Classifer(int Target, int Angle, float Payoff, float Aware, String Act){
	super(Target, Angle, Aware);
	PayoffExpected = Payoff;
	Action = Act;
	}

public void SetAction(String Act){
	Action = Act;
}

public void SetTAware(float Aware){
	TargetAwareness = Aware;
}

public void SetDistance(int Target){
	TargetDistance = Target;
}

public void SetAngle(int Angle){
	TargetAngle = Angle;
}

public void SetPayoff(float Payoff){
	PayoffExpected = Payoff;
}
}
