import java.util.ArrayList;

public class Euler {


    private int phi;
    private int n;
    private int p;
    private ArrayList<Integer> coprime_arr_list;
    private ArrayList<Integer> prime_arr_list;

   public Euler(int n){
        this.n=n;
        this.p=n;// will be used in getCoprime cycle
        this.coprime_arr_list = new ArrayList<>();
        this.prime_arr_list = new ArrayList<>();
    }


    public int getPhi(){
        double result =n;

        // Высчитываем все простые множители для числа n
        //  простые числа p умножаем
        //по такому правилу (1 - 1/p)
        for(int p=2;p*p<=n;p++){

            if (this.n%p==0){

              while (n%p==0)
                  n/=p;

                  result*=(1.0-(1.0/(double) p));

              }
    }   // если значние простого числа больше кв корня числа n
        // (Может быть не более одного простого)

        if(n>1) {
        result*=(1.0-(1.0/(double) n));
    }
        return this.phi=(int)result;
    }

    public ArrayList<Integer> getPrime(int n){
       int div = 2;
        while (n > 1)
        {
            while (n % div == 0)
            {

                n = n / div;
                prime_arr_list.add(div);
            }
            div++;
        }

       return this.prime_arr_list;
    }
    public ArrayList<Integer> getCoprime()
    {


        for (int i=1;i<p;i++){
            if(new Euclide().getGCD(p,i)==1){
                int tmp = i;
                coprime_arr_list.add(tmp);
        }
        }


        return coprime_arr_list;
    }



    public void printCoprime(){
        System.out.print("Coprime: ");
       for(int i=0;i<coprime_arr_list.size();i++){

           System.out.print(coprime_arr_list.get(i)+" ");
       }

       System.out.println("\n");
    }



}
