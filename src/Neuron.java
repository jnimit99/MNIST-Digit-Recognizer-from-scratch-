import java.util.Random;

public class Neuron {
	public double value=0.0;
	public double[] weights;
	public double delta=0.0;
	public double bias=(new Random().nextDouble())-0.5;
	public Neuron(int s)
	{
		weights=new double[s];
		for(int i=0;i<s;i++)
			weights[i]=(new Random().nextDouble())-0.5;
	}
}
