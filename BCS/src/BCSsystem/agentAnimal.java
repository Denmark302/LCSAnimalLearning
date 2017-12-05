package BCSsystem;

public class agentAnimal {
public int topSpeed;
public int startSpeed;
public int Speed;
public int acceleration;
public double Awareness;
public int TurnSpeed;


public agentAnimal(int Top, int accel, int Turn){
	topSpeed = Top;
	startSpeed = 0;
	acceleration = accel;
	Awareness = 0.0;
	Speed = 0;
	TurnSpeed = Turn;
	}
public void SetTop(int Top){
	topSpeed = Top;
}

public void SetStart(int start){
	startSpeed = start;
}

public void SetSpeed(int speed){
	Speed = speed;
}

public void SetAwareness(double aware){
	Awareness = aware;
}

public void SetTurn(int turn){
	TurnSpeed = turn;
}

public void Accelerate(){
	if((topSpeed - Speed) < acceleration){
		Speed = topSpeed;
	}
	else{
		Speed += acceleration;
	}
}
}
