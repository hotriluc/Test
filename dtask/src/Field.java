
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Field {

    private int[][] array;
    private int P;
    private int teta;
    private int teta_min;
    private ArrayList<Integer> teta_arr_list;
    private int signal_arr[];


    public Field(int P) {
        this.array = new int[6][P - 1];
        this.P = P;
        this.teta = 2;
        this.teta_arr_list = new ArrayList<>();
        this.signal_arr = new int[P - 1];

    }


    public void setArray(int[][] array, int P) {

        this.array = new int[6][P];
    }

    public int[][] getArray() {

        return this.array;
    }

    public int getP() {

        return this.P;
    }

    public void setP(int P) {

        this.P = P;
    }

    public ArrayList<Integer> getTetaList(int teta_min, ArrayList<Integer> coprime_arr_list) {
        for (int i = 0; i < coprime_arr_list.size(); i++) {
            int tmp = coprime_arr_list.get(i);
            double tmp_teta = Math.pow(teta_min, tmp) % this.P;//минимальный первообразный возводим в степень(взаимнопрост с p)
            int rd = (int) Math.round(tmp_teta);
            teta_arr_list.add(rd);
        }
        return this.teta_arr_list;
    }

    public void printTetaList() {

        for (int i = 0; i < teta_arr_list.size(); i++) {

            System.out.printf("\u03F4" + (i + 1) + " = " + teta_arr_list.get(i) + "\n");
        }
    }

    public int getTeta() {

        return this.teta;
    }

    public void setTeta(int teta) {
        this.teta = teta;
    }

    public void fill_row_Ui() {
        for (int i = 0; i < getP() - 1; i++) {
            array[0][i] = i + 1;
        }
    }

    public void fill_row_ai() {

        for (int i = 0; i < (getP() - 1); i++) {
            int p = getP();
            BigInteger b = BigInteger.valueOf(i);
            BigInteger a = BigInteger.valueOf(teta);
            BigInteger c = a.modPow(b,BigInteger.valueOf(p));

            array[1][i] = c.intValue();
        }
        // по известной половине поля можно заполнить оставшуюся часть по формуле a(p-1)/2+n=p-an
      /*  for (int i = (getP()-1)/2;i<getP()-1;i++){
            int n = i-(getP()-1)/2;
            array[1][i]=getP()-array[1][n];
        }*/


    }

    /*Заполнение Ai*/
    public void fill_row_Ai() {
        int tmp = 0;
        int position = 0;
        for (int i = 0; i < getP() - 1; i++) {
            tmp = array[0][i];//Из первой строки Ui выибираем эл

            position = array[1][i];//Каждой Ui соответствует ai, запоминаем ai в качестве позиции для Ai
            array[2][position - 1] = tmp;//Заносим Ui в сохраненую позицию
        }
    }

    public void fill_row_bi() {
        int tmp = 0;
        for (int i = 0; i < getP() - 1; i++) {
            tmp = array[1][i];
            array[3][i] = (tmp + 1) % P;
            if (array[3][i] == (0)) {
                array[3][i] = 1;
            }
        }
    }

    public void fill_row_MH() {
        int pos1, pos2;
        int tmp = 0;
        for (int i = 0; i < getP() - 1; i++) {
            pos1 = array[3][i];//значение bi cохраняем в pos
            for (int j = 0; j < getP() - 1; j++) {
                if (array[0][j] == pos1) {
                    /*далее проходим по всем Ui и сравниваем его с pos(bi)
                        если Ui = bi то выполняем следующие действия
                    */
                    pos2 = array[0][j] - 1;//запоминаем Ui в pos2 для дальнейшего обрашения к Ai
                    tmp = array[2][pos2];// сохраняем Apos2
                    break;//выходим из цикла
                }
            }
            array[4][i] = tmp % P;//заносим в MH(i) значение Apos2

        }
    }

    public void fill_row_Psi() {
        int tmp = 0;
        for (int i = 0; i < getP() - 1; i++) {
            if (array[4][i] % 2 == 0) {
                array[5][i] = -1;
            } else {
                array[5][i] = 1;
            }
        }
    }

    public int[] getPsi() {
        for (int i = 0; i < getP() - 1; i++) {
            signal_arr[i] = array[5][i];
        }
        return this.signal_arr;
    }

    public void printArray() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < getP() - 1; j++) {
                System.out.printf("%6d", array[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /*Нахождение TETA_MIN
    * Выбипается teta_min = 2 и дальше проверяется
    * Высчитывается функция эйлера для числа p
    * далее результат от функции эйлера раскладывается на множители,
    * результат от функции эйлера делим на множители и получаем множество чисел которые будут тестироваться
    * для нахождения первообразного. Обозначим как test_power {a, b ,c .. z}
    * тестирование проходит следующим образом teta_min^test_power mod p
    * если teta_min при всех тестируемых значениях дало результат не = 1, то число является первообрзным
    * если же хотя бы 1 дало результат 1, то число не первообразный ,teta_min+=1
    * */
    public int getTeta_min(Euler e){
        teta_min=2;
        int tmp_phi=e.getPhi();
        ArrayList<Integer> pl = e.getPrime(tmp_phi);
        Set<Integer> test_power = new HashSet<>();
        for(int i:pl){
            System.out.printf("%d ",i);
            test_power.add(i);
        }
        List<Integer> list = new ArrayList<>(test_power);
        System.out.println("\nSet of powers to test size is "+test_power.size());
        int cnt=0;
        int ind = 0;
        while (cnt<list.size()) {
            BigInteger b = BigInteger.valueOf(tmp_phi / list.get(ind));
            BigInteger a = BigInteger.valueOf(teta_min);
            BigInteger c = a.modPow(b,BigInteger.valueOf(getP()));
            if (c.intValue()==1) {
                teta_min++;
                cnt=0;
                ind=0;
            } else {
                cnt++;
                ind++;
            }
        }

        return teta_min;
    }



    }
