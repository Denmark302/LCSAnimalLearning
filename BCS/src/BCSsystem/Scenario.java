package BCSsystem;

public class Scenario {
	public float TargetAwareness;
	public int TargetDistance;
	public int TargetAngle;


	public Scenario(int Target, int Angle, float Aware){
		TargetAwareness = Aware;
		TargetDistance = Target;
		TargetAngle = Angle;

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

	

}
