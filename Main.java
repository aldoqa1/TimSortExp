
import java.util.ArrayList;

public class Main {

    static public void main(String[] args) {

        //arreglo de numeros
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 2, 2, 2, 2, 2, 3, 4, 5, 6, 6, 8, 9, 7, 6, 6, 6, 6, 6, 6, 8, 4, 6, 6, 6, 6, 6, 6, 6, 6, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 8};

        //tamano de los min runs. normalmente es entre 32 y 64
        int min_run = 6;

        //arreglo que contiene los indices de lo runs
        ArrayList<Integer> runs = new ArrayList<>();

        //variables direccionales
        boolean isAscendent = true; //orden ascendente o no
        boolean orderIsSet = false; //si el orden ya fue implementado
        boolean isBroken = false; //si ya se quebro del orden del run actual
        boolean itwasreversed = false; //si el run actual ya se hizo reverse
        int count = 0; //contador de elementos por cada run
        int end = -1; //terminacion del run actual
        int lastend; //ultimo indice alcanzado osea la terminacion del ultimo run
        boolean brokenAfter = true; //para saber si el ciclo de quebro antes o despues de alcanzar el minimo de runs

        //acomodando numeros en runs
        for (int i = 1; i < numbers.length; i++) {

            //setea el orden si es ascendente o descendente
            if (!orderIsSet) {
                if (numbers[i] != numbers[i - 1]) {
                    isAscendent = numbers[i] >= numbers[i - 1];
                    orderIsSet = true;
                } else {
                    count++;
                    continue;
                }
            }

            //si el orden es ascendente
            if (isAscendent) {
                if (numbers[i] < numbers[i - 1]) {
                    isBroken = true;
                    //si se quiebra en el momento que es menor al min run, se va a setar brokenafter como false, indicando que este fue quebrado antes de llegar al min run, en caso contrario true, que indica que se quebro al moento de llegar al run o despues
                    brokenAfter = (count >= min_run);
                }
                //si el orden es descendente
            } else {
                if (numbers[i] > numbers[i - 1]) {
                    isBroken = true;
                    //si se quiebra en el momento que es menor al min run, se va a setar brokenafter como false, indicando que este fue quebrado antes de llegar al min run, en caso contrario true, que indica que se quebro al moento de llegar al run o despues
                    brokenAfter = (count > min_run);
                }
            }

            //revisa si ya se rompio el orden y si el contador de runs es menor al de min_run
            if (isBroken && count < min_run) {

                //revisa que no sea ascendente y que no haya sido reversado ya, en caso de que sea desendenter y no haya sido reverseado, se reversea
                if (!isAscendent && !itwasreversed) {
                    reverse(numbers, end + 1, i);
                    itwasreversed = true;
                }

                //finalmente se suma el contador de ese run y pasa al siguiente ciclo hata completar el run min
                count++;
                continue;
            }

            //en caso de que ya se rompio el orden cuando el minrun ya es completado o cuando estamos en el ultimo elemento, es hora de terminar el run y comenzar con otro nuevo
            if ((isBroken && count >= min_run) || i == (numbers.length - 1)) {

                lastend = end; //se actualiza el inicio del index del run
                end = (numbers.length - 1 == i) ? i : i - 1; //se actualiza el final del run actual

                //cuando no sea ascendente ni haya sido reverseado
                if (!isAscendent && !itwasreversed) {

                    //invertir arreglo actual
                    reverse(numbers, lastend + 1, end);

                    //en caso de que se haya quebrado en el min run o despues
                    if (brokenAfter) {
                        //System.out.printf("descendente y aplicar solo reverse [%d..%d]%n", lastend + 1, end);

                        //si es el ultimo elemento, forzar insertion sort si el ultimo penultimo elemento es menor al ultimo
                        if (i == numbers.length - 1 && (numbers[i] > numbers[i - 1])) {
                            InsertionSort.generateInsertion(numbers, lastend + 1, end);
                            //System.out.printf("forzando insertion");
                        }

                    } else {
                        InsertionSort.generateInsertion(numbers, lastend + 1, end);
                        //System.out.printf("descendente, aplicar reverse y luego insertion sort [%d..%d]%n", lastend + 1, end);
                    }

                }

                //cunado no sea ascendente y haya sido reverseado
                if (!isAscendent && itwasreversed) {
                    //solo aplicar insertion sort
                    InsertionSort.generateInsertion(numbers, lastend + 1, end);
                    //System.out.printf("descendente y ya se aplico reverse, necesitamos insertion sort [%d..%d]%n", lastend + 1, end);
                }

                //cando es ascendente y cuando fue quebrado antes
                if (isAscendent && !brokenAfter) {
                    //solo aplicar insertion sort
                    InsertionSort.generateInsertion(numbers, lastend + 1, end);
                    //System.out.printf("ascendente y quebro antes del 6, se aplica InsertionSort [%d..%d ]%n", lastend + 1, end);

                }

                //cuando es ascendente y fue quebrado despues
                if (isAscendent && brokenAfter) {
                    //System.out.printf("ascendente no se requiere nada [%d..%d ]%n", lastend + 1, end);
                    //ultimo elemento, forzar insertion sort si el ultimo elemento es menor al penultimo
                    if (i == numbers.length - 1 && (numbers[i] < numbers[i - 1])) {
                        InsertionSort.generateInsertion(numbers, lastend + 1, end);
                        // System.out.printf("forzando insertion");
                    }

                }

                //terminar 
                runs.add(end); //se agrega el index del run actual
                isBroken = false; //se resetar is broken
                orderIsSet = false; //se resetea el si el orden esta seteado
                itwasreversed = false; //se resetea itwasreversed
                count = 0; //se resetea el contador

            }

            count++;
        }

        MergeSort sort = new MergeSort();

        numbers = sort.mergeRuns(numbers, runs.stream().mapToInt(Integer::intValue).toArray());

        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }

    }

    //hace reverse entre un index y otro de un array
    public static void reverse(int[] numbers, int idxStart, int idxEnd) {
        while (idxStart < idxEnd) {
            int temp = numbers[idxStart];
            numbers[idxStart] = numbers[idxEnd];
            numbers[idxEnd] = temp;
            idxStart++;
            idxEnd--;
        }
    }

}
