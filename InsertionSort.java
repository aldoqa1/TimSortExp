public class InsertionSort{
    
    public InsertionSort(){

    }
    
    public static void generateInsertion(int[] numbers, int idxStart, int idxEnd){

        for(int i=idxStart; i<=idxEnd; i++){

            int j= i;
            int number= numbers[i];

            //revisa hacia atras si el numero actual es menos que los anteriores para mover esos numeros a la derecha, dejando un espacio en blanco para insertar el numero en ese index al final
            while(j>idxStart && (number<numbers[j-1])  ){
                numbers[j] = numbers[j-1];
                j--;
            }

            //setea el numero en su posicion ordenada
            numbers[j] = number;
        }

    }
        




}