import java.util.Random;



public class InputLayer {

	private double[] firstInput =new double[Consts.InputNums];
	private double[][] firstWeight=new double[Consts.InputNums][Consts.HidenNums];
	private double[][] secondWeight = new double[Consts.HidenNums][Consts.outNums];
	private double[] sum1=new double[Consts.HidenNums];
	private double[] sum2=new double[Consts.outNums];
	private double[] hidenOut=new double[Consts.HidenNums];
    double[] y1={0,0,0,0,0,0,1};
	double[] y2={1,1,1,1,1,1,1};
	double[] y;

	public double[] getHidenOutput(double[] input){
		
		double[] hidenOutput=new double[Consts.HidenNums];
		for(int j=0;j<Consts.HidenNums;j++){
			for(int i=0;i<Consts.InputNums;i++){
				sum1[j]+=input[i]*firstWeight[i][j];
			}
			hidenOutput[j]=1/(1+Math.pow(Math.E, -sum1[j]));
		}		
		return hidenOutput;
	}
	public double[] getOutput(double[] input){
		double[] output=new double[Consts.outNums];
		hidenOut=getHidenOutput(input);
		for(int j=0;j<Consts.outNums;j++){
			for(int i=0;i<Consts.HidenNums;i++){
				sum2[j]+=hidenOut[i]*secondWeight[i][j];
			}
			double tmp=1/(1+Math.pow(Math.E, -sum2[j]));
			if(tmp<0.0001){
				output[j]=0d;				
			}
			else{
				output[j]=tmp;
			}
		}
		return output;
	}
	public void TrainWeights(double[] out){
		
		//bp
		double delt2[]=new double[Consts.outNums];
		double delt1[]=new double[Consts.HidenNums];
		double deltw2[][] =new double[Consts.HidenNums][Consts.outNums];
		double deltw1[][] =new double[Consts.InputNums][Consts.HidenNums];
		
		//train deltw2
		for(int j=0;j<Consts.HidenNums;j++){
			for(int k=0;k<Consts.outNums;k++){
				delt2[k]=out[k]*(1-out[k])*(y[k]-out[k]);
				deltw2[j][k]=Consts.step*delt2[k]*hidenOut[j];
				secondWeight[j][k]+=deltw2[j][k];
			}
		}		
		//tain deltw1
		for(int i=0;i<Consts.InputNums;i++){
			for(int j=0;j<Consts.HidenNums;j++){
				double higherDelt=0;
				for(int k=0;k<Consts.outNums;k++){
					higherDelt=delt2[k]*secondWeight[j][k];
				}
				delt1[j]=hidenOut[j]*(1-hidenOut[j])*higherDelt;
				deltw1[i][j]=Consts.step*delt1[j]*firstInput[i];
				firstWeight[i][j] +=deltw1[i][j]; 
			}			
		}
	}
	
	
	public InputLayer() {
		// TODO Auto-generated constructor stub
		
		//initialize input
		Random random1=new Random();


		//initialize weights
		for(int i=0;i<Consts.InputNums;i++){
			for(int j=0;j<Consts.HidenNums;j++){
				double htmp=(random1.nextDouble()-0.5);
                firstWeight[i][j]=htmp;
			}
		}
		for(int i=0;i<Consts.HidenNums;i++){
			for(int j=0;j<Consts.outNums;j++){
				double htmp=(random1.nextDouble()-0.5);
                secondWeight[i][j]=htmp;
			}
		}
		
		
		///////////test
		int n=0;
		while(n++<200){
			System.out.println("µÚ "+(n)+"´Î");
			double[] out;
			if(n%2==0){
				for(int i=0;i<Consts.InputNums;i++){
					firstInput[i]=-1;
				}
				y=y1;
			}else{				
				for(int i=0;i<Consts.InputNums;i++){
					firstInput[i]=1;
				}
				y=y2;
			}
			
			out=getOutput(firstInput);
			TrainWeights(out);
			for(int k=0;k <Consts.outNums;k++){
				System.out.println(out[k]);
			}
		}
		for(int i=0;i<Consts.InputNums;i++){
			firstInput[i]=-1;
		}
		System.out.println("this is inputtest");
		for(int k=0;k <Consts.outNums;k++){
			System.out.println(getOutput(firstInput)[k]);
		}
		Random testRandom=new Random();
		System.out.println(testRandom.nextDouble()-0.5);

	}
	public static void main(String[] args) {			
		new InputLayer();
	}
}
