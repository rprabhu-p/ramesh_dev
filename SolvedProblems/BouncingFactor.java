import java.util.Scanner;

class BouncingFactor {
    public static void main(String args[]) {
        //To get height
        Scanner input = new Scanner(System.in);

        //To get bounce
        float height = input.nextFloat();
        float bounce = input.nextFloat();

        input.close();

        float travelled = 0;

        try {
            if((bounce < 0) && (bounce > 1)) {
                System.out.println("Wrong bouncing value.");
                return;
            }

            travelled = 0;
            float temp = 0;

            while(height > 1) {
                float bounced = height * bounce;

                if(bounced < 1) {
                    temp = height;
                } else {
                    temp = height + bounced;
                }
                travelled = travelled + temp;
                height = bounced;
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(travelled);
    }
}