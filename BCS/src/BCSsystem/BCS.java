package BCSsystem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
public class BCS {
	
	public static void main(String[] args) {
		//String[] Actions = { "Pounce", "Charge", "Sneak", "Adjust+Charge", "Adjust+Sneak"};
		String[] Actions = {"Charge"};
		Random Generator = new Random();
		float intensity;
		ArrayList<Classifer> Classifers = new ArrayList<Classifer>();
		agentAnimal Predator = new agentAnimal((Generator.nextInt(10) + 35), (Generator.nextInt(3) + 5), 30);
		for( int u = 0; u < 10; u++){
		
			float ActPayoff = 0.0f;
			ArrayList<Classifer> ActionSet = new ArrayList<Classifer>(); 
			Scenario Scene = new Scenario ((Generator.nextInt(61) + 100), 180, (round(Generator.nextFloat(), 1)));
			agentAnimal Prey = new agentAnimal((Generator.nextInt(10) + 60), (Generator.nextInt(6) + 20), 60);
			for(int i = 0; i < Actions.length; i++){
				ActionSet.add(new Classifer(Scene.TargetDistance, Scene.TargetAngle, 0.3f, Scene.TargetAwareness, Actions[i]));
			}
			if(Classifers.size() != 0){
				for(int x = 0; x < ActionSet.size(); x++){
					Classifer temp = ActionSet.remove(x);
					temp = AdoptPayoff(temp, Classifers);
					if(temp.PayoffExpected < 0.1f){
						temp.SetPayoff(0.1f);
					}
					ActionSet.add(temp);
					for(int y = 0; y < Classifers.size(); y++){
						if( temp.Action.equals(Classifers.get(y).Action) && ClassSimular(temp, Classifers.get(y)) > 0.9f ){
							Classifers.remove(y);
						}
					}
				}
			}
			Classifer Action = ActionSet.remove(WeightSelection(ActionSet));
			Classifer Copy = Action;
			String currentAction = Action.Action;
			//Initial check if prey is aware
			/*if(Action.TargetAwareness >= 1.0f){
				System.out.println("Prey is aware of predators presence");
				if(Action.TargetDistance <= Predator.topSpeed/2){
					currentAction = "Pounce";
				}
				else{
					currentAction = "Charge";
				}
			}*/
			float aware = Action.TargetAwareness;
			//Unaware phase
			boolean conclude = false;
			int angle = Action.TargetAngle;
			while(aware < 1.0f && !conclude){
				if(currentAction.contains("Adjust")){
					//
				}
				if(currentAction.equals("Pounce")){
					//
				}
				else if(currentAction.equals("Charge")){
					Action.SetDistance(Action.TargetDistance -= Predator.Speed);
					if(Action.TargetDistance <= 0){
						ActPayoff = 1.0f;
						conclude = true;
						System.out.println("Prey captured");
					}
					else{
						Predator.Accelerate();
					}
					intensity = intensityCheck(Predator.Speed, Action.TargetDistance);
					aware = awarenessCheck(aware, intensity);
					System.out.println("awareness is " + aware);
					System.out.println();
					
				}
				else if(currentAction.equals("Sneak")){
					//
				}
				if(aware >= 1.0f){
					System.out.println("Prey is now aware of the predators presence");
					/*if(Action.TargetDistance <= Predator.topSpeed/2){
						currentAction = "Pounce";
					}
					else{
						currentAction = "Charge";
					}*/
				}
			}
			
			//aware phase
			while(!conclude){
				if(angle < 180){
					angle += Prey.TurnSpeed;
					if(angle > 180){
						angle = 180;
					}
				}
				if(currentAction.equals("Charge")){
					Prey.Accelerate();
					Action.SetDistance( Action.TargetDistance += SpeedEfficent(angle, Prey.Speed));
					System.out.println("Prey is moving at " + Prey.Speed);
					Predator.Accelerate();
					Action.SetDistance(Action.TargetDistance -=  Predator.Speed);
					System.out.println("Predator is moving at " + Predator.Speed);
					if(Action.TargetDistance <= 0){
						Action.TargetDistance = 0;
						conclude = true;
						ActPayoff = 1.0f;
						System.out.println("Prey captured");
					}
					else if(Action.TargetDistance > 0 && SpeedEfficent(angle, Prey.Speed) >= Predator.Speed){
						conclude = true;
						ActPayoff = 1 - round( (float)(Action.TargetDistance / Copy.TargetDistance) , 1);
						System.out.println("Prey got away");
					}
				}
			}
			Copy.SetPayoff(ActPayoff);
			ActionSet.add(Copy);
			Classifers.addAll(ActionSet);
		}
		for(int t = 0; t < Classifers.size(); t++){
			System.out.println("Classifer #" + t);
			System.out.println("Action was " + Classifers.get(t).Action);
			System.out.println("Distance from prey was " + Classifers.get(t).TargetDistance);
			System.out.println("Prey inital Angle was " + Classifers.get(t).TargetAngle);
			System.out.println("Prey inital awareness was  " + Classifers.get(t).TargetAwareness);
			System.out.println("Payoff of classifer is " + Classifers.get(t).PayoffExpected);
		}
	}
	public static int SpeedEfficent(int angle, int speed){
		float efficent =  (round(((float)((angle/90) - 1)) , 1 ));
		if(efficent > 0){
			float finalspeedfloat = efficent * speed;
			return Math.round(finalspeedfloat);
		}
		return 0;
	}
	/*public static float intensityCheck(int speed, int maxspeed, int distance){
		float speedRatio = (round((speed/maxspeed),1));
		System.out.println("speedRatio is " + speedRatio);
		System.out.println("distance is " + distance);
		
	*/}
	public static float awarenessCheck(float aware, float intensity){
		System.out.println("aware is " + aware);
		System.out.println("intensity is " + intensity);
		Random chance = new Random();
		float awareFloat = round((aware * intensity), 1);
		float result = round(chance.nextFloat(), 1);
		System.out.println("result is " + result);
		System.out.println("aware rate is " +awareFloat);
		System.out.println();
		if(result <= awareFloat){
			System.out.println("prey is more aware");
			return aware+awareFloat;
			
		}
		return aware;
	}
	
	public static float round(float d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
	public static Classifer AdoptPayoff(Classifer Act, ArrayList<Classifer> Comp){
			float Sim = 0.0f;
			int location = 0;
			float maxSim = 0.0f;
			Classifer ret = Act;
			for(int i = 0; i < Comp.size(); i ++){
				if(Act.Action.equals(Comp.get(i).Action)){
					Sim = ClassSimular(Act,Comp.get(i));
					//add equality case
					if(Sim > maxSim){
						location = i;
						maxSim = Sim;
					}
				}
			}
			ret.SetPayoff(round((Comp.get(location).PayoffExpected * maxSim), 1));
			return ret;
		
	}
	
	public static float ClassSimular(Classifer A, Classifer B){
		float Sim = 0.0f;
		int larger = Math.max(A.TargetAngle, B.TargetAngle);
		int smaller = Math.min(A.TargetAngle, B.TargetAngle);
		Sim += smaller/larger;
		larger = Math.max(A.TargetDistance, B.TargetDistance);
		smaller = Math.min(A.TargetDistance, B.TargetDistance);
		Sim += smaller/larger;
		larger = Math.max(A.TargetAngle, B.TargetAngle);
		smaller = Math.min(A.TargetAngle, B.TargetAngle);
		Sim += smaller/larger;
		Sim /= 3;
		Sim = round(Sim, 1);
		return Sim;
		
	}
	
	public static int WeightSelection(ArrayList<Classifer> ActionSet){
		ArrayList<Integer> Select = new ArrayList<Integer>();
		Random Selector = new Random();
		for(int i = 0; i < ActionSet.size(); i++){
			int count = (int) (ActionSet.get(i).PayoffExpected * 10);
			for(int x = 0; x < count; x++){
				Select.add(i);
			}
		}
		int position = Select.get(Selector.nextInt(Select.size()));
		return position;
	}

}
