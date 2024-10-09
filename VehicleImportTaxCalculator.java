import java.util.Scanner;

public class VehicleImportTaxCalculator {

    static final int FF_FLAT = 20000;
    static final int SD_FLAT = 35000;
    static final int IL_FLAT = 150000;
    static final int ED_FLAT = 200000;
    static final int APS_COST = 300000;
    static final int DPS_COST = 700000;
    static final int IL_OLD_VEHICLE_PERCENT = 15;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the vehicle type (Ambulance, Estate, Sedan, SUV, Trailer): ");
        String vehicleType = scanner.nextLine();

        System.out.println("Enter CIF value (Cost, Insurance, Freight): ");
        double cif = scanner.nextDouble();

        System.out.println("Enter the age of the vehicle (in years): ");
        int vehicleAge = scanner.nextInt();

        double importDuty = 0, vat = 0, wht = 0, il = IL_FLAT;
        int plateCost = 0;
        boolean isOldVehicle = vehicleAge > 10;

        switch (vehicleType.toLowerCase()) {
            case "ambulance":
                plateCost = DPS_COST;
                if (isOldVehicle) il = IL_OLD_VEHICLE_PERCENT * cif / 100;
                break;
            case "estate":
                importDuty = 0.25 * cif;
                vat = 0.18 * cif;
                wht = 0.06 * cif;
                il = calculateEstateIL(vehicleAge, cif);
                plateCost = choosePlateSystem(scanner);
                break;
                    case "sedan":
                    importDuty = 0.25 * cif;
                    vat = 0.18 * cif;
                    wht = 0.06 * cif;
                    il = calculateSedanIL(vehicleAge, cif);
                    plateCost = DPS_COST;
                break;
                    case "suv":
                    importDuty = 0.25 * cif;
                    vat = 0.18 * cif;
                    wht = 0.06 * cif;
                    il = calculateSUVIL(vehicleAge, cif);
                    plateCost = APS_COST;
                break;
                    case "trailer":
                    importDuty = 0.25 * cif;
                    vat = 0.18 * cif;
                    wht = 0.06 * cif;
                    il = calculateTrailerIL(vehicleAge, cif);
                    plateCost = choosePlateSystem(scanner);
                break;
            default:
                System.out.println("Invalid vehicle type.");
                return;
        }

        printTaxDetails(importDuty, vat, wht, il, plateCost);
        scanner.close();
    }

    public static double calculateEstateIL(int age, double cif) {
        if (age > 10) return 0.15 * cif;
        return IL_FLAT;
    }

    public static double calculateSedanIL(int age, double cif) {
        if (age > 15) {
            System.out.println("Sedan older than 15 years cannot be imported.");
            System.exit(0);
        }
        if (age > 10) return 0.10 * cif;
        if (age > 5) return 0.05 * cif;
        return 0.015 * cif;
    }

    public static double calculateSUVIL(int age, double cif) {
        if (age > 10) return 0.25 * cif;
        if (age > 5) return 0.15 * cif;
        return 0.01 * cif;
    }

    public static double calculateTrailerIL(int age, double cif) {
        if (age > 15) {
            System.out.println("Trailer older than 15 years cannot be imported.");
            System.exit(0);
        }
        if (age > 10) return 0.10 * cif;
        if (age > 5) return 0.05 * cif;
        return 0.015 * cif;
    }

    public static int choosePlateSystem(Scanner scanner) {
        System.out.println("Enter plate system (APS or DPS): ");
        String plateSystem = scanner.next();
        if (plateSystem.equalsIgnoreCase("APS")) return APS_COST;
        else if (plateSystem.equalsIgnoreCase("DPS")) return DPS_COST;
        else {
            System.out.println("Invalid plate system.");
            System.exit(0);
        }
        return 0;
    }

    public static void printTaxDetails(double importDuty, double vat, double wht, double il, int plateCost) {
        System.out.println("\nTaxes Breakdown:");
        System.out.printf("Import Duty (ID): %.2f\n", importDuty);
        System.out.printf("Value Added Tax (VAT): %.2f\n", vat);
        System.out.printf("Withholding Tax (WHT): %.2f\n", wht);
        System.out.printf("Infrastructure Levy (IL): %.2f\n", il);
        System.out.println("Stamp Duty (SD): " + SD_FLAT);
        System.out.println("Form Fees (FF): " + FF_FLAT);
        System.out.println("Excise Duty (ED): " + ED_FLAT);
        System.out.println("Registration (Plate Cost): " + plateCost);
    }
}