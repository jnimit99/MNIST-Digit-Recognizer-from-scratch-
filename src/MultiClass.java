import java.util.*;
import java.lang.Math;

public class MultiClass {
	public static int[] TotalLayers= {784,30,10};
	public static int no_of_classes=10;

	public layer[] Layers;
	public static double alpha=0.001; //learning rate
	public static double lambda=1.0;
	public void CreateNet()
	{
		Layers=new layer[TotalLayers.length];
		Layers[0]=new layer(TotalLayers[0],0);
		for(int i=1;i<TotalLayers.length;i++)
			Layers[i]=new layer(TotalLayers[i],TotalLayers[i-1]);
	}
	
	public static double Activation(double x)
	{
		return 1.0/(1.0+Math.exp(-lambda*x));
	}
	
	public static double Derivative(double x)
	{
		return lambda*x*(1.0-x);
	}
	
	public void FeedForward(List<Integer> Input,double[] Output)
	{
        for(int i=0;i<TotalLayers[0];i++)
        {
            Layers[0].neurons[i].value=Input.get(i);
        }
		for(int i=1;i<Layers.length;i++)
		{
			for(int j=0;j<TotalLayers[i];j++)
			{
				double val=Layers[i].neurons[j].bias;
				for(int k=0;k<TotalLayers[i-1];k++)
					val+=Layers[i].neurons[j].weights[k]*Layers[i-1].neurons[k].value;
				Layers[i].neurons[j].value=Activation(val);
			}
		}
		for(int i=0;i<TotalLayers[Layers.length-1];i++)
		{
			Output[i]=Layers[Layers.length-1].neurons[i].value;
		}
	}
	
	public void BackPropagate(double[] Target,double[] Output)
	{
		double err;
		for(int i=0;i<TotalLayers[Layers.length-1];i++)
		{
			err=Target[i]-Output[i];
			Layers[Layers.length-1].neurons[i].delta=err*Derivative(Output[i]);
		}
		for(int i=Layers.length-2;i>=0;i--)
		{
			for(int j=0;j<TotalLayers[i];j++)
			{
				double error=0.0;
				for(int k=0;k<TotalLayers[i+1];k++)
					error+=Layers[i+1].neurons[k].delta*Layers[i+1].neurons[k].weights[j];
				Layers[i].neurons[j].delta=error*Derivative(Layers[i].neurons[j].value);
			}
			for(int j=0;j<TotalLayers[i+1];j++)
			{
			    Layers[i+1].neurons[j].bias+=alpha*Layers[i+1].neurons[j].delta;
				for(int k=0;k<TotalLayers[i];k++)
					Layers[i+1].neurons[j].weights[k]+=alpha*Layers[i+1].neurons[j].delta*Layers[i].neurons[k].value;
			}
		}
	}

	public double error()
	{
		double err=0.0;
		for(int i=0;i<TotalLayers[Layers.length-1];i++)
			err+=Math.pow(Layers[Layers.length-1].neurons[i].delta,2);
		err=err/2;
		return err;
	}

	public static int maxClass(double[] Output)
	{
		int maxIndex =0;
		double max=Output[0];
		for(int i=1;i<10;i++)
		{
			if(max<Output[i])
			{
				max=Output[i];
				maxIndex=i;
			}
		}
		return maxIndex;
	}

	/*public void error()
    {
        double err=0.0;
        for(int i=0;i<)
    }*/

    public void save_weights()
    {
        for(int l=1;l<TotalLayers.length;l++)
        {
            for(int i=0;i<TotalLayers[l];i++)
            {
                String line="";
                for(int j=0;j<TotalLayers[l-1];j++)
                {
                    line+=Double.toString(Layers[l].neurons[i].weights[j]);
                    line+=",";
                }

            }
        }
    }
	
	public static void main(String[] args)
	{
		csv_read train=new csv_read("C://Users//hp//eclipse-workspace//MultiClass//train.csv");
		//csv_read test=new csv_read("C://Users//hp//eclipse-workspace//MultiClass//test.csv");
		ArrayList<ArrayList<Integer>> dataset=train.getdata();
		//ArrayList<ArrayList<Integer>> testset=test.getdata();

		MultiClass net=new MultiClass();
        net.CreateNet();
        double error=0.0;
		int epochs=250;
		double[] Output=new double[no_of_classes];
		List<Integer> Input=new ArrayList<Integer>();
        long a=System.currentTimeMillis();
        System.out.println("time"+System.currentTimeMillis());
        //training
		//System.out.println(dataset.size());
		for(int i=0;i<epochs;i++) {
            System.out.println("epoch "+i);
            for (int j = 0; j <32000;j++) {
                Input = dataset.get(j).subList(1, 785);
				double tar = dataset.get(j).get(0);
                double[] Target = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                Target[(int)tar] = 1;
                net.FeedForward(Input,Output);
                net.BackPropagate(Target,Output);
                error+=net.error();
            }
            error/=250;
			System.out.println(error);
        }
		//testing
		double[] Test_Output=new double[no_of_classes];
		int count=0;
		for(int i=32000;i<42000;i++)
		{
			Input = dataset.get(i).subList(1, 785);
			double Tar = dataset.get(i).get(0);
			net.FeedForward(Input,Test_Output);
			int Out=maxClass(Test_Output);
			if(Out==Tar)
				count++;
		}
		System.out.println("Accuracy="+((float)count/10000));

		//System.out.println("time"+(System.currentTimeMillis()-a));
		  //  System.out.println(maxClass(Test_Output));

	}
}

