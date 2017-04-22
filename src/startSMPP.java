import com.techstudio.app.Start;


public class startSMPP{	
	
	
	public static void main(String arg[]){

		System.out.println("arg:"+arg.length);
		if(arg != null && arg.length >= 1)
			new Start(arg[0]);
		else
			new Start();
	}
	
}
