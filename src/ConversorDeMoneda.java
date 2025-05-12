import java.util.Scanner;

public class ConversorDeMoneda {
    public static final String API_KEY = "615ae419b77e101c453df843";
    public static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            menu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    convertir("USD", "ARS");
                    break;
                case 2:
                    convertir("ARS", "USD");
                    break;
                case 3:
                    convertir("USD", "BRL");
                    break;
                case 4:
                    convertir("BRL", "USD");
                    break;
                case 5:
                    convertir("USD", "COP");
                    break;
                case 6:
                    convertir("COP", "USD");
                    break;
                case 7:
                    System.out.println("Gracias por usar el conversor!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elija una opción válida.");
            };
        } while (opcion != 7);
    }

    public static void menu() {
        System.out.println("""
                *********************************
                Bienvenido al Conversor de moneda!
                
                1) Dólar --> Peso Argentino
                2) Peso Argentino --> Dólar
                3) Dólar --> Real Brasileño
                4) Real Brasileño --> Dólar
                5) Dólar --> Peso Colombiano
                6) Peso Colombiano --> Dólar
                7) Salir
                Elija una opción válida: 
                *********************************
                """);
    }

    public static void convertir(String monedaOrigen, String monedaDestino) {
        try {
            ConsultaCambio consultaCambio = new ConsultaCambio();
            double tasaDeCambio = consultaCambio.obtenerTasaDeCambio(monedaOrigen, monedaDestino);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el valor que desea convertir: ");
            double cantidad = scanner.nextDouble();

            double resultado = cantidad * tasaDeCambio;
            System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);
        } catch (RuntimeException e) {
            System.out.println("Error al obtener la tasa de cambio " + e.getMessage());
        }
    }
}
