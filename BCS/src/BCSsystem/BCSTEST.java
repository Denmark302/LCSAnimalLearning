package BCSsystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class BCSTEST {
	
	public static void main(String[] args) {
		String[] Actions = {"Charge", "Sneak", "Adjust+Charge", "Adjust+Sneak"};
		Random Generator = new Random();
		float intensity;
		int runcounter = 0;
		int partcount = 0;
		int halfcount = 0;
		int counter = 0;
		float maxfit = 0.0f;
		Classifer Action;
		Classifer Copy;
		Classifer Classcopy = null;
		int loc = 0;
		ArrayList<Classifer> Classifers = new ArrayList<Classifer>();
		agentAnimal Predator = new agentAnimal((Generator.nextInt(10) + 35), (Generator.nextInt(3) + 5), 30);
		for( int u = 0; u < 10000; u++){
			float ActPayoff = 0.0f;
			ArrayList<Classifer> ActionSet = new ArrayList<Classifer>();
			//Scenario Scene = new Scenario ((Generator.nextInt(61) + 100), 180, (round(0.0f + Generator.nextFloat() * 0.3f, 1)));
			Scenario Scene = new Scenario (100, 180, 0.3f);
			agentAnimal Prey = new agentAnimal((Generator.nextInt(10) + 60), (Generator.nextInt(6) + 15), 60);
			for(int i = 0; i < Actions.length; i++){
				ActionSet.add(new Classifer(Scene.TargetDistance, Scene.TargetAngle, 0.2f, Scene.TargetAwareness, Actions[i]));
			}
			if(Classifers.size() != 0){
				maxfit = 0.0f;
				for(int y = 0; y < Classifers.size(); y++){
					if(ClassSimular(ActionSet.get(0), Classifers.get(y)) > maxfit){
						maxfit = ClassSimular(ActionSet.get(0), Classifers.get(y));
						loc = y;
					}
					for(int i = 0; i < ActionSet.size(); i++){
						if(ActionSet.get(i).Action.equals(Classifers.get(y).Action)){
							Copy = CopyClassifer(ActionSet.get(i));
							Classcopy = CopyClassifer(Classifers.get(y));
							Copy.SetPayoff(round((maxfit * Classifers.get(y).PayoffExpected), 1));
							ActionSet.set(i, Copy);
						}
					}	
				}
			}
			Action = ActionSet.remove(WeightSelection(ActionSet));

			if(Action.Action == "Charge"){
				Action.SetPayoff(1.0f);
				counter++;
			}
			else if(Action.Action == "Sneak"){
				Action.SetPayoff(0.2f);
			}
			else if(Action.Action == "Adjust+Charge"){
				Action.SetPayoff(0.5f);
				halfcount++;
			}
			else if(Action.Action == "Adjust+Sneak"){
				Action.SetPayoff(0.1f);
			}
			runcounter++;
			if(maxfit == 1.0f){
				for(int x = 0; x < Classifers.size(); x++ ){
					if(Action.Action.equals((Classifers).get(x).Action) && ClassSimular(Action, Classifers.get(x)) == 1.0f){
						if(Action.PayoffExpected < Classifers.get(x).PayoffExpected){
							Action.SetPayoff(round( (Action.PayoffExpected + Classifers.get(x).PayoffExpected)/2, 1));
							Classifers.remove(x);
						}
						else{
							Classifers.remove(x);
						}
					}
				}
			}
			Classifers.add(Action);
		}
		
		for(int t = 0; t < Classifers.size(); t++){
			System.out.println("Classifer #" + t);
			System.out.println("Action is " + Classifers.get(t).Action);
			System.out.println("Distance from prey initally " + Classifers.get(t).TargetDistance);
			System.out.println("Prey inital Angle " + Classifers.get(t).TargetAngle);
			System.out.println("Prey inital awareness  " + Classifers.get(t).TargetAwareness);
			System.out.println("Payoff expected " + Classifers.get(t).PayoffExpected);
		}
		System.out.println("Prey was almost caught " +partcount+" times");
		System.out.println("Prey was halfway caught " +halfcount+" times");
		System.out.println("Prey was caught " +counter+" times");
		System.out.println("out of " + runcounter + " attempts");
		System.out.println("sucess rating of " + round((float)counter/runcounter ,1));
		System.out.println("partial sucess rating of " + round((float)partcount/runcounter ,1));
		System.out.println("halfway sucess rating of " + round((float)halfcount/runcounter ,1));
		
	}
	public static int SpeedEfficent(int angle, int speed){
		float efficent =  (round(((float)((angle/90) - 1)) , 1 ));
		if(efficent > 0){
			float finalspeedfloat = efficent * speed;
			return Math.round(finalspeedfloat);
		}
		return 0;
	}
	
	public static float intensityCheck(agentAnimal ani, int distance, Classifer check){
		float distanceratio = 1.0f - (round(( (float)distance/check.TargetDistance), 1));
		float speedRatio = (round(((float)ani.Speed/ani.topSpeed),1));
		float finalratio = (round (((float)speedRatio * distanceratio), 1));
		System.out.println("speedRatio is " + speedRatio);
		System.out.println("distanceratio is " + distanceratio);
		System.out.println("intensity is " + finalratio);
		return finalratio;
		
	}
	
	public static Classifer CopyClassifer(Classifer original){
		Classifer ret= new Classifer(original.TargetDistance, original.TargetAngle, original.PayoffExpected,original.TargetAwareness,original.Action);
		return ret;
	}
	public static float awarenessCheck(float aware, float intensity){
		System.out.println("aware is " + aware);
		System.out.println("intensity is " + intensity);
		Random chance = new Random();
		float awareFloat = round(((aware + intensity)/2), 1);
		float result = round(chance.nextFloat(), 1);
		System.out.println("result is " + result);
		System.out.println("aware rate is " +awareFloat);
		System.out.println();
		if(result <= awareFloat){
			if(awareFloat <= 0.0f){
				return aware + 0.1f;
			}
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
					//Add equality case
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
		float large = Math.max(A.TargetAwareness, B.TargetAwareness);
		float small = Math.min(A.TargetAwareness, B.TargetAwareness);
		if(small == 0.0f && large == 0.0f || small == large){
			Sim += 1.0f;
		}
		else{
			Sim += round( small/large ,1);
		}
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