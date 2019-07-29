
public class layer {
	public int Units;
	public Neuron[] neurons;
	
	layer(int n,int s)
	{
		Units=n;
		neurons=new Neuron[n];
		for(int i=0;i<n;i++)
			neurons[i]= new Neuron(s);
		
	}
	
}
