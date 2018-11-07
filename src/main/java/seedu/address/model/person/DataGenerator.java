package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.appointment.Type;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.diet.DietType;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.Timestamp;
import seedu.address.model.medicine.Dose;
import seedu.address.model.medicine.Duration;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.VisitorList;

//@@author snajef
/**
 * Generator for example data for product demo.
 *
 */
public class DataGenerator {
    // The maximum number of rows that fit on a single table when maximised.
    private static final int MAX_NUMBER_OF_ROWS = 14;

    private final NricGenerator nricGenerator = new NricGenerator();
    private final NameGenerator nameGenerator = new NameGenerator();
    private final PhoneGenerator phoneGenerator = new PhoneGenerator();
    private final EmailGenerator emailGenerator = new EmailGenerator();
    private final AddressGenerator addressGenerator = new AddressGenerator();
    private final DrugAllergyGenerator drugAllergyGenerator = new DrugAllergyGenerator();
    private final PrescriptionListGenerator prescriptionListGenerator = new PrescriptionListGenerator();
    private final AppointmentsListGenerator appointmentsListGenerator = new AppointmentsListGenerator();
    private final MedicalHistoryGenerator medicalHistoryGenerator = new MedicalHistoryGenerator();
    private final DietCollectionGenerator dietCollectionGenerator = new DietCollectionGenerator();
    private final VisitorListGenerator visitorListGenerator = new VisitorListGenerator();

    public Nric generateNric() {
        return nricGenerator.generate();
    }

    public Name generateName() {
        return nameGenerator.generate();
    }

    public Phone generatePhone() {
        return phoneGenerator.generate();
    }

    public Email generateEmail() {
        return emailGenerator.generate();
    }

    public Address generateAddress() {
        return addressGenerator.generate();
    }

    public Set<Tag> generateDrugAllergies() {
        return drugAllergyGenerator.generate();
    }

    public PrescriptionList generatePrescriptionList() {
        return prescriptionListGenerator.generate();
    }

    public AppointmentsList generateAppointmentsList() {
        return appointmentsListGenerator.generate();
    }

    public MedicalHistory generateMedicalHistory() {
        return medicalHistoryGenerator.generate();
    }

    public DietCollection generateDietCollection() {
        return dietCollectionGenerator.generate();
    }

    public VisitorList generateVisitorList() {
        return visitorListGenerator.generate();
    }

    /**
     * Generates a random integer from {@code from} to {@code to},
     * inclusive of from and exclusive of to.
     */
    public int randomInt(int from, int to) {
        return ThreadLocalRandom.current()
            .nextInt(from, to);
    }

    /**
     * Returns a random element from the array given.
     */
    public <T> T getRandom(T[] arr) {
        return arr[randomInt(0, arr.length)];
    }

    /**
     * Returns a LocalDate representing a randomised date
     * in the range of [0, 30) days before the current date.
     */
    public LocalDate getRandomPastLocalDate() {
        int shift = randomInt(0, 30);
        LocalDate toReturn = LocalDate.now();

        return toReturn.minusDays(shift);
    }

    /**
     * Returns a LocalDate representing a randomised date
     * in the range of [0, 30) days after the current date.
     */
    public LocalDate getRandomFutureLocalDate() {
        int shift = randomInt(0, 30);
        LocalDate toReturn = LocalDate.now();

        return toReturn.plusDays(shift);
    }

    /** Returns a LocalTime representing a randomised time
     * in the range of [0900, 1700].
     */
    public LocalTime getRandomLocalTime() {
        return LocalTime.MIN.plus(randomInt(9, 17) * 60 + randomInt(0, 60), ChronoUnit.MINUTES);
    }

    /**
     * Generates NRICs.
     */
    class NricGenerator implements Generator<Nric> {
        private String[] prefixes = { "S", "T", "F", "G" };
        private String[] suffixes = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", // "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        private Set<Integer> generatedBefore = new HashSet<>();

        @Override
        public Nric generate() {
            return new Nric(prefixes[ThreadLocalRandom.current()
                                     .nextInt(0, prefixes.length)]
                                         + String.format("%07d", generateNricDigits())
                                         + suffixes[ThreadLocalRandom.current()
                                                    .nextInt(0, suffixes.length)]);
        }

        /**
         * Generates the numerical digits for the NRIC.
         * Guarantees no duplicate NRICs generated.
         */
        int generateNricDigits() {
            int candidate = randomInt(6500000, 9999999);
            while (generatedBefore.contains(candidate)) {
                candidate = randomInt(6500000, 9999999);
            }
            generatedBefore.add(candidate);
            return candidate;
        }
    }

    /**
     * Generates names.
     */
    class NameGenerator implements Generator<Name> {
        private String[] firstNames = {
            "Emma",
            "Liam",
            "Olivia",
            "Noah",
            "Ava",
            "Oliver",
            "Isabella",
            "Mason",
            "Sophia",
            "Lucas",
            "Amelia",
            "Elijah",
            "Mia",
            "Logan",
            "Charlotte",
            "Ethan",
            "Harper",
            "Aiden",
            "Mila",
            "James",
            "Aria",
            "Jackson",
            "Avery",
            "Carter",
            "Ella",
            "Sebastian",
            "Luna",
            "Alexander",
            "Evelyn",
            "Benjamin",
            "Sofia",
            "Jacob",
            "Abigail",
            "Michael",
            "Layla",
            "William",
            "Scarlett",
            "Daniel",
            "Riley",
            "Grayson",
            "Ellie",
            "Jack",
            "Emily",
            "Leo",
            "Lily",
            "Luke",
            "Chloe",
            "Jayden",
            "Madison",
            "Henry",
            "Camila",
            "Wyatt",
            "Zoey",
            "Gabriel",
            "Penelope",
            "Julian",
            "Elizabeth",
            "Owen",
            "Victoria",
            "Jaxon",
            "Grace",
            "Levi",
            "Bella",
            "David",
            "Nora",
            "Matthew",
            "Aubrey",
            "Mateo",
            "Hannah",
            "Muhammad",
            "Aurora",
            "Asher",
            "Stella",
            "Adam",
            "Addison",
            "John",
            "Skylar",
            "Josiah",
            "Maya",
            "Lincoln",
            "Hazel",
            "Ryan",
            "Paisley",
            "Nathan",
            "Natalie",
            "Samuel",
            "Savannah",
            "Caleb",
            "Nova",
            "Isaac",
            "Violet",
            "Joseph",
            "Emilia",
            "Eli",
            "Elena",
            "Isaiah",
            "Niamey",
            "Anthony",
            "Eva",
            "Hunter"
        };

        private String[] lastNames = {
            "Nguyen",
            "Lee",
            "Kim",
            "Patel",
            "Tran",
            "Chen",
            "Li",
            "Le",
            "Wang",
            "Yang",
            "Singh",
            "Wong",
            "Pham",
            "Park",
            "Lin",
            "Liu",
            "Chang",
            "Huang",
            "Wu",
            "Zhang",
            "Chan",
            "Khan",
            "Shah",
            "Huynh",
            "Yu",
            "Lam",
            "Choi",
            "Kaur",
            "Vang",
            "Ho",
            "Chung",
            "Truong",
            "Xiong",
            "Phan",
            "Vu",
            "Vo",
            "Lim",
            "Lu",
            "Tang",
            "Cho",
            "Ngo",
            "Cheng",
            "Kang",
            "Tan",
            "Ng",
            "Dang",
            "Do",
            "Hoang",
            "Ly",
            "Hong",
            "Han",
            "Bui",
            "Ahmed",
            "Ma",
            "Chu",
            "Sharma",
            "Ali",
            "Xu",
            "Zheng",
            "Duong",
            "Song",
            "Liang",
            "Zhou",
            "Kumar",
            "Sun",
            "Lau",
            "Thao",
            "Zhao",
            "Chin",
            "Zhu",
            "Shin",
            "Leung",
            "Jiang",
            "Hu",
            "Thomas",
            "Reyes",
            "Santos",
            "Gupta",
            "Cheung",
            "Lai",
            "Desai",
            "Oh",
            "Cruz",
            "Cao",
            "Yee",
            "Hwang",
            "Yi",
            "Ha",
            "Dinh",
            "Garcia",
            "Jung",
            "Lo",
            "Hsu",
            "Chau",
            "Yoon",
            "Luu",
            "Mai",
            "Chow",
            "Trinh",
            "He",
            "Young",
            "Fong",
            "Rahman",
            "Her",
            "Luong",
            "Moua",
            "Mehta",
            "Doan",
            "Hussain",
            "Ramos",
            "Kwon",
            "Yoo",
            "Ko",
            "Kong",
            "Smith",
            "Chiu",
            "Su",
            "Delacruz",
            "Shen",
            "Tam",
            "Chong",
            "Mendoza",
            "Pan",
            "Gao",
            "Begum",
            "Guo",
            "Dong",
            "Vue",
            "Woo",
            "Chowdhury",
            "Mathew",
            "Thai",
            "Lor",
            "Bautista",
            "Jain",
            "Tong",
            "Yan",
            "Chun",
            "Dao",
            "Johnson",
            "Tsai",
            "Feng",
            "Gill",
            "Jin",
            "Pak",
            "Malik",
            "Ahn",
            "Joseph",
            "George",
            "Luo",
            "Shi",
            "Fernandez",
            "Fang",
            "Ahmad",
            "Quach",
            "Ye",
            "Xie",
            "Joshi",
            "Islam",
            "Chou",
            "Rao",
            "Cha",
            "Vuong",
            "Wei",
            "Flores",
            "Chao",
            "Lopez",
            "Deguzman",
            "Syed",
            "Moon",
            "Tu",
            "Jang",
            "To",
            "Ong",
            "Son",
            "Yun",
            "Reddy",
            "Yuan",
            "Eng",
            "Gonzales",
            "Liao",
            "Kwan",
            "Cai",
            "Peng",
            "Abraham",
            "Fan",
            "Chiang",
            "Rivera",
            "Perez",
            "Amin",
            "Fu",
            "Fung",
            "Aquino",
            "Siddiqui",
            "Deng",
            "Villanueva",
            "An",
            "Ta",
            "Williams",
            "Leong",
            "Yeung",
            "Zeng",
            "Choe",
            "Brown",
            "Deleon",
            "Van",
            "Mei",
            "Du",
            "John",
            "Parikh",
            "Lei",
            "Castillo",
            "Guan",
            "Phung",
            "Sung",
            "So",
            "Castro",
            "Kuo",
            "Prasad",
            "Suh",
            "Seo",
            "Yuen",
            "Varghese",
            "Hua",
            "Yao",
            "Louie",
            "Sandhu",
            "Pang",
            "Alam",
            "Tsang",
            "Yeh",
            "Min",
            "Gandhi",
            "Martinez",
            "Martin",
            "Nair",
            "Jones",
            "Miller",
            "Torres",
            "Hung",
            "Hernandez",
            "Rodriguez",
            "Das",
            "Persaud",
            "Tanaka",
            "Yin",
            "Saechao",
            "Chi",
            "David",
            "Domingo",
            "Qureshi",
            "Nakamura",
            "Yamamoto",
            "Xiao",
            "Hsieh",
            "Moy",
            "Rana",
            "Hang",
            "Au",
            "Diep",
            "La",
            "Bae",
            "Sanchez",
            "Tse",
            "Nam",
            "Yip",
            "King",
            "Long",
            "Kao",
            "Wen",
            "Hossain",
            "Jacob",
            "Yim",
            "Lui",
            "Chaudhry",
            "Santiago",
            "Davis",
            "Thach",
            "Tom",
            "Shaikh",
            "Gong",
            "Hui",
            "Kwong",
            "Quan",
            "Lew",
            "Mao",
            "Wan",
            "Gu",
            "Jeong",
            "Kwok",
            "Sato",
            "Shrestha",
            "Ramirez",
            "Mohammed",
            "Roy",
            "Agarwal",
            "Dizon",
            "Iqbal",
            "Verma",
            "Uddin",
            "Soriano",
            "Ling",
            "Tolentino"
        };

        @Override
        public Name generate() {
            return new Name(
                firstNames[randomInt(0, firstNames.length)] + " " + lastNames[randomInt(0, lastNames.length)]);
        }

    }

    /**
     * Generates phone numbers.
     */
    class PhoneGenerator implements Generator<Phone> {
        @Override
        public Phone generate() {
            return new Phone(String.format("9%07d", randomInt(1000000, 9999999)));
        }
    }

    /**
     * Generates email addresses.
     */
    class EmailGenerator implements Generator<Email> {
        private String[] suffixes = new String[] {
            "gmail.com",
            "hotmail.com",
            "mail.com",
            "yahoo.com.sg",
            "yuntong.com",
            "email.com",
            "msn.com",
        };

        @Override
        public Email generate() {
            return new Email("example@" + suffixes[randomInt(0, suffixes.length)]);
        }
    }

    /**
     * Generates addresses.
     */
    class AddressGenerator implements Generator<Address> {
        private String[] streetNames = {
            "Adam Park",
            "Airport Road",
            "Alexandra Road",
            "Balmoral Park",
            "Battery Road",
            "Bencoolen street",
            "Cecil Street",
            "Church Street",
            "Cross Street",
            "Devonshire Road",
            "Dublin Road",
            "Duxton road",
            "East Coast Road",
            "Eden Grove",
            "Edgware Road",
            "Fifth Avenue",
            "First Avenue",
            "First Street",
            "George Street",
            "Glasgow Road",
            "Grange Road",
            "Havelock Road",
            "High Street",
            "Hill Street",
            "International Business Park",
            "International Road",
            "Irving Place",
            "Jalan Ampang",
            "Jalan Kuala",
            "Jubilee Road",
            "Kensington Park Road",
            "Kitchener Road",
            "Knights Bridge",
            "Lancaster Gate",
            "Leicester Road",
            "Lengkok Bahru",
            "Market Street",
            "Middle Road",
            "Mount Elizabeth",
            "Napier Road",
            "Nathan Road",
            "Newton Road",
            "Ocean Drive",
            "One Tree Hill",
            "Orchard Road",
            "Palmer Road",
            "Park Crescent",
            "Park Lane",
            "Quality Road",
            "Queen Street",
            "Queensway",
            "Regent Street",
            "Research Link",
            "Ridley Park",
            "Sixth Avenue",
            "Somerset Road",
            "Stanley Street",
            "Temple Street",
            "Thomson Road",
            "Tras Street",
            "Unity Street",
            "University Road",
            "University Walk",
            "Valley Road",
            "Victoria Lane",
            "Victoria Street",
            "Waterloo Street",
            "West Coast Highway",
            "West Coast Park",
            "Xilin Avenue",
            "York Hill",
            "York Place",
            "York Road",
            "Zion Road"
        };

        @Override
        public Address generate() {
            return new Address(String.format("%03d", randomInt(1, 1000)) + ", "
                + streetNames[randomInt(0, streetNames.length)] + ", "
                + "#" + String.format("%02d", randomInt(1, 41)) + "-" + String.format("%02d", randomInt(1, 41)));
        }
    }

    /**
     * Generates drug allergies.
     */
    class DrugAllergyGenerator implements Generator<Set<Tag>> {
        private String[] drugAllergies = {
            "Amoxicillin",
            "Ampicillin",
            "Penicillin",
            "Tetracycline",
            "Ibuprofen",
            "Naproxen",
            "Cetuximab",
            "Rituximab",
            "Abacavir",
            "Nevirapine",
            "Insulin",
            "Carbamazepine",
            "Lamotrigine",
            "Phenytoin",
            "Atracurium",
            "Succinylcholin",
            "Vecuronium"
        };

        @Override
        public Set<Tag> generate() {
            Set<Tag> toReturn = new HashSet<>();

            for (int i = 0; i < drugAllergies.length; i++) {
                if (randomInt(0, 2) == 0) {
                    continue;
                }
                try {
                    toReturn.add(ParserUtil.parseTag(drugAllergies[i]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return toReturn;
        }
    }

    /**
     * Generates prescriptions and stores them in a {@code PrescriptionList}.
     */
    class PrescriptionListGenerator implements Generator<PrescriptionList> {
        // Yes, I know the generated medications won't make any sense.
        // Deal with it.

        private Set<String> drugNamesUsedBefore = new HashSet<>();

        private String[] drugNames = {
            "Acetaminophen",
            "Acyclovir",
            "Adalimumab",
            "Albuterol",
            "Albuterol Sulfate",
            "Alendronate Sodium",
            "Allopurinol",
            "Alprazolam",
            "Amiodarone Hydrochloride",
            "Amitriptyline",
            "Amlodipine Besylate",
            "Amlodipine Besylate",
            "Amoxicillin",
            "Amphetamine",
            "Anastrozole",
            "Apixaban",
            "Aripiprazole",
            "Aspirin",
            "Atenolol",
            "Atomoxetine Hydrochloride",
            "Atorvastatin",
            "Azithromycin",
            "Bacitracin",
            "Baclofen",
            "Benazepril Hydrochloride",
            "Benzonatate",
            "Brimonidine Tartrate",
            "Budesonide",
            "Budesonide",
            "Bupropion",
            "Buspirone Hydrochloride",
            "Calcium",
            "Canagliflozin",
            "Carisoprodol",
            "Carvedilol",
            "Celecoxib",
            "Cephalexin",
            "Cetirizine Hydrochloride",
            "Chlorthalidone",
            "Cholecalciferol",
            "Ciprofloxacin",
            "Citalopram",
            "Clindamycin",
            "Clobetasol Propionate",
            "Clonazepam",
            "Clonidine",
            "Clopidogrel Bisulfate",
            "Cyanocobalamin",
            "Cyclobenzaprine",
            "Desogestrel",
            "Dextroamphetamine",
            "Diazepam",
            "Diclofenac",
            "Dicyclomine Hydrochloride",
            "Digoxin",
            "Diltiazem Hydrochloride",
            "Divalproex Sodium",
            "Docusate",
            "Donepezil Hydrochloride",
            "Doxazosin Mesylate",
            "Doxycycline",
            "Drospirenone",
            "Duloxetine",
            "Enalapril Maleate",
            "Ergocalciferol",
            "Escitalopram Oxalate",
            "Esomeprazole",
            "Estradiol",
            "Ethinyl Estradiol",
            "Ezetimibe",
            "Famotidine",
            "Fenofibrate",
            "Ferrous Sulfate",
            "Finasteride",
            "Fluconazole",
            "Fluoxetine Hydrochloride",
            "Fluticasone",
            "Fluticasone Propionate",
            "Folic Acid",
            "Furosemide",
            "Gabapentin",
            "Gemfibrozil",
            "Glimepiride",
            "Glipizide",
            "Glyburide",
            "Guanfacine",
            "Hydralazine Hydrochloride",
            "Hydrochlorothiazide",
            "Hydrocodone Bitartrate",
            "Hydrocortisone",
            "Hydroxychloroquine Sulfate",
            "Hydroxyzine",
            "Ibuprofen",
            "Insulin Aspart",
            "Insulin Detemir",
            "Insulin Glargine",
            "Insulin Human",
            "Insulin Lispro",
            "Irbesartan",
            "Isosorbide Mononitrate",
            "Lamotrigine",
            "Lansoprazole",
            "Latanoprost",
            "Levetiracetam",
            "Levofloxacin",
            "Levothyroxine",
            "Lidocaine",
            "Liraglutide",
            "Lisdexamfetamine Dimesylate",
            "Lisinopril",
            "Lithium",
            "Loratadine",
            "Lorazepam",
            "Losartan Potassium",
            "Lovastatin",
            "Magnesium",
            "Meclizine Hydrochloride",
            "Meloxicam",
            "Memantine Hydrochloride",
            "Mesalamine",
            "Metformin Hydrochloride",
            "Methocarbamol",
            "Methylphenidate",
            "Methylprednisolone",
            "Metoprolol",
            "Metronidazole",
            "Mirtazapine",
            "Mometasone",
            "Montelukast",
            "Morphine",
            "Naproxen",
            "Nebivolol Hydrochloride",
            "Nifedipine",
            "Nitrofurantoin",
            "Nitroglycerin",
            "Olmesartan Medoxomil",
            "Omega--acid Ethyl Esters",
            "Omeprazole",
            "Ondansetron",
            "Oxcarbazepine",
            "Oxybutynin",
            "Oxycodone",
            "Pantoprazole Sodium",
            "Paroxetine",
            "Phentermine",
            "Phenytoin",
            "Pioglitazone Hydrochloride",
            "Polyethylene Glycol ",
            "Potassium",
            "Pramipexole Dihydrochloride",
            "Pravastatin Sodium",
            "Prednisolone",
            "Prednisone",
            "Pregabalin",
            "Promethazine Hydrochloride",
            "Propranolol Hydrochloride",
            "Quetiapine Fumarate",
            "Ramipril",
            "Ranitidine",
            "Risperidone",
            "Rivaroxaban",
            "Ropinirole Hydrochloride",
            "Rosuvastatin Calcium",
            "Sertraline Hydrochloride",
            "Simvastatin",
            "Sitagliptin Phosphate",
            "Sodium",
            "Solifenacin Succinate",
            "Spironolactone",
            "Sulfamethoxazole",
            "Sumatriptan",
            "Tamsulosin Hydrochloride",
            "Temazepam",
            "Terazosin",
            "Testosterone",
            "Timolol",
            "Tiotropium",
            "Tizanidine",
            "Topiramate",
            "Tramadol Hydrochloride",
            "Trazodone Hydrochloride",
            "Triamcinolone",
            "Valacyclovir",
            "Valsartan",
            "Venlafaxine Hydrochloride",
            "Verapamil Hydrochloride",
            "Warfarin",
            "Zolpidem Tartrate",
            "Yuntongazole",
            "Zhiyuropium",
            "Jefferin",
            "Jiaxinovir"
        };

        private Double[] doses = {
            1.0,
            2.0,
            3.0,
            4.0,
            5.0,
            6.0,
            7.0,
            8.0,
            9.0,
            10.0,
            11.0
        };

        private String[] dosageUnits = {
            "125mg tablets",
            "250mg tablets",
            "500mg tablets",
            "1000mg tablets",
            "ml",
            "pills",
            "capsules",
            "aerosol",
            "inhaler",
            "vaporizer",
            "ear drops",
            "eye drops",
            "skin patch",
            "suppository"
        };

        private Integer[] dosesPerDay = {
            1,
            2,
            3,
            4,
            5
        };

        private Integer[] durations = {
            2,
            7,
            14,
            28,
            30,
        };

        String getRandomDrugName() {
            String candidate = getRandom(drugNames);

            while (drugNamesUsedBefore.contains(candidate)) {
                candidate = getRandom(drugNames);
            }

            drugNamesUsedBefore.add(candidate);
            return candidate;
        }

        @Override
        public PrescriptionList generate() {
            PrescriptionList toReturn = new PrescriptionList();

            // Pick a random number of minimum prescriptions for the sample data to look good.
            int numPrescriptions = randomInt(8, MAX_NUMBER_OF_ROWS + 1);

            for (int i = 0; i < numPrescriptions; i++) {
                try {
                    Duration duration = new Duration(getRandom(durations));
                    duration.shiftDateRange(getRandomPastLocalDate());
                    Prescription p = new Prescription(getRandomDrugName(),
                        new Dose(getRandom(doses), getRandom(dosageUnits), getRandom(dosesPerDay)),
                        duration);
                    toReturn.add(p);
                } catch (IllegalValueException e) {
                    // All our inputs are guaranteed valid, so this really shouldn't ever happen.
                    e.printStackTrace();
                    continue;
                }
            }

            drugNamesUsedBefore.clear();

            return toReturn;
        }
    }

    /**
     * Generates appointments and stores them in a {@code AppointmentsList}.
     */
    class AppointmentsListGenerator implements Generator<AppointmentsList> {
        private String[] props = {
            "Auscultation",
            "Medical inspection",
            "Palpation",
            "Percussion",
            "Vital signs measurement"
        };

        private String[] diags = {
            "Biopsy test",
            "Blood test",
            "Stool test",
            "Urinalysis",
            "Cardiac stress test",
            "Electrocardiography",
            "Electrocorticography",
            "Electroencephalography",
            "Electromyography",
            "Electroneuronography",
            "Electronystagmography",
            "Electrooculography",
            "Electroretinography",
            "Endoluminal capsule monitoring",
            "Endoscopy",
            "Colonoscopy",
            "Colposcopy",
            "Cystoscopy",
            "Gastroscopy",
            "Laparoscopy",
            "Laryngoscopy",
            "Ophthalmoscopy",
            "Otoscopy",
            "Sigmoidoscopy",
            "Esophageal motility study",
            "Evoked potential",
            "Magnetoencephalography",
            "Medical imaging",
            "Angiography",
            "Aortography",
            "Cerebral angiography",
            "Coronary angiography",
            "Lymphangiography",
            "Pulmonary angiography",
            "Ventriculography",
            "Chest photofluorography",
            "Computed tomography",
            "Echocardiography",
            "Electrical impedance tomography",
            "Fluoroscopy",
            "Magnetic resonance imaging",
            "Diffuse optical imaging",
            "Diffusion tensor imaging",
            "Diffusion-weighted imaging",
            "Functional magnetic resonance imaging",
            "Positron emission tomography",
            "Radiography",
            "Scintillography",
            "SPECT",
            "Ultrasonography",
            "Contrast-enhanced ultrasound",
            "Gynecologic ultrasonography",
            "Intravascular ultrasound",
            "Obstetric ultrasonography",
            "Thermography",
            "Virtual colonoscopy",
            "Neuroimaging",
            "Posturography"
        };

        private String[] therapeutics = {
            "Thrombosis prophylaxis",
            "Precordial thump",
            "Politzerization",
            "Hemodialysis",
            "Hemofiltration",
            "Plasmapheresis",
            "Apheresis",
            "Extracorporeal membrane oxygenation (ECMO)",
            "Cancer immunotherapy",
            "Cancer vaccine",
            "Cervical conization",
            "Chemotherapy",
            "Cytoluminescent therapy",
            "Insulin potentiation therapy",
            "Low-dose chemotherapy",
            "Monoclonal antibody therapy",
            "Photodynamic therapy",
            "Radiation therapy",
            "Targeted therapy",
            "Tracheal intubation",
            "Unsealed source radiotherapy",
            "Virtual reality therapy",
            "Physical therapy/Physiotherapy",
            "Speech therapy",
            "Phototerapy",
            "Hydrotherapy",
            "Heat therapy",
            "Shock therapy",
            "Insulin shock therapy",
            "Electroconvulsive therapy",
            "Symptomatic treatment",
            "Fluid replacement therapy",
            "Palliative care",
            "Hyperbaric oxygen therapy",
            "Oxygen therapy",
            "Gene therapy",
            "Enzyme replacement therapy",
            "Intravenous therapy",
            "Phage therapy",
            "Respiratory therapy",
            "Vision therapy",
            "Electrotherapy",
            "Transcutaneous electrical nerve stimulation (TENS)",
            "Laser therapy",
            "Combination therapy",
            "Occupational therapy",
            "Immunization",
            "Vaccination",
            "Immunosuppressive therapy",
            "Psychotherapy",
            "Drug therapy",
            "Acupuncture",
            "Antivenom",
            "Magnetic therapy",
            "Craniosacral therapy",
            "Chelation therapy",
            "Hormonal therapy",
            "Hormone replacement therapy",
            "Opiate replacement therapy",
            "Cell therapy",
            "Stem cell treatments",
            "Intubation",
            "Nebulization",
            "Inhalation therapy",
            "Particle therapy",
            "Proton therapy",
            "Fluoride therapy",
            "Cold compression therapy",
            "Animal-Assisted Therapy",
            "Negative Pressure Wound Therapy",
            "Nicotine replacement therapy",
            "Oral rehydration therapy"
        };

        private String[] surgicals = {
            "Ablation",
            "Amputation",
            "Biopsy",
            "Cardiopulmonary resuscitation (CPR)",
            "Cryosurgery",
            "Endoscopic surgery",
            "Facial rejuvenation",
            "General surgery",
            "Hand surgery",
            "Hemilaminectomy",
            "Image-guided surgery",
            "Knee cartilage replacement therapy",
            "Laminectomy",
            "Laparoscopic surgery",
            "Lithotomy",
            "Lithotriptor",
            "Lobotomy",
            "Neovaginoplasty",
            "Radiosurgery",
            "Stereotactic surgery",
            "Radiosurgery",
            "Vaginoplasty",
            "Xenotransplantation"
        };

        @Override
        public AppointmentsList generate() {
            AppointmentsList toReturn = new AppointmentsList();
            int numAppointments = randomInt(3, MAX_NUMBER_OF_ROWS + 1);


            for (int i = 0; i < numAppointments; i++) {
                Type type = getRandom(Type.values());
                String[] procedureNames = { "Default value" };

                switch (type) {
                case PROPAEDEUTIC:
                    procedureNames = props;
                    break;
                case DIAGNOSTIC:
                    procedureNames = diags;
                    break;
                case THERAPEUTIC:
                    procedureNames = therapeutics;
                    break;
                case SURGICAL:
                    procedureNames = surgicals;
                    break;
                default:
                    break;
                }

                toReturn.add(new Appointment(
                    type,
                    getRandom(procedureNames),
                    Appointment.DATE_TIME_FORMAT.format(
                        LocalDateTime.of(getRandomFutureLocalDate(), getRandomLocalTime())),
                    "Dr. " + getRandom(nameGenerator.firstNames)));
            }

            return toReturn;
        }
    }

    /**
     * Generates medical histories and stores them in a {@code MedicalHistory}.
     */
    class MedicalHistoryGenerator implements Generator<MedicalHistory> {
        private String[] descriptions = {
            "Saw patient today for recurrent cough and flu. Suspect patient has cancer.",
            "Example diagnosis.",
            "Saw patient today for recurrent nightmares involving CS2103T finals. Suspect patient needs to git gud.",
            "LGTM.",
            "Lorum ipsum.",
            "Saw patient today. Symptoms: wet, chesty cough, runny nose. Suspect patient might have pneumonia."
        };

        @Override
        public MedicalHistory generate() {
            int numDiagnoses = randomInt(1, MAX_NUMBER_OF_ROWS + 1);
            MedicalHistory toReturn = new MedicalHistory();

            for (int i = 0; i < numDiagnoses; i++) {
                toReturn.add(new Diagnosis(getRandom(descriptions), "Dr. " + getRandom(nameGenerator.firstNames),
                    new Timestamp(LocalDateTime.of(getRandomPastLocalDate(), getRandomLocalTime()))));
            }

            return toReturn;
        }
    }
    /**
     * Generates dietary restrictions and stores them in a {@code DietCollection}.
     */
    class DietCollectionGenerator implements Generator<DietCollection> {
        private String[] allergies = {
            "Shellfish",
            "Prawns",
            "Chocolate",
            "Eggs",
            "Dairy",
            "Gluten",
            "The left feelers of adolescent prawns below the age of 2-1/2 years",
            "Jalapeno spaghetti"
        };

        private String[] culturalRestrictions = {
            "Halal",
            "Kosher",
            "Vegetarian",
            "Vegan",
            "Liquid food diet"
        };

        private String[] physicalDifficulties = {
            "Hands cannot move",
            "Mouth cannot move",
            "Lips cannot move",
            "Sensitive teeth",
            "Fingers cannot move",
            "Feet cannot move",
            "Cannot sit upright",
            "Cannot lie down",
            "Cannot move left pinky toe IF current day is the third Sunday of the month"
        };

        @Override
        public DietCollection generate() {
            DietCollection toReturn = new DietCollection();
            int numDietRestrictions = randomInt(0, MAX_NUMBER_OF_ROWS + 1);

            for (int i = 0; i < numDietRestrictions; i++) {
                DietType rand = getRandom(DietType.values());
                String detail;

                switch (rand) {
                case ALLERGY:
                    detail = getRandom(allergies);
                    break;
                case CULTURAL:
                    detail = getRandom(culturalRestrictions);
                    break;
                case PHYSICAL:
                    detail = getRandom(physicalDifficulties);
                    break;
                default:
                    detail = "Yuntong";
                    break;
                }

                toReturn.add(new Diet(detail, rand));
            }

            return toReturn;
        }
    }

    /**
     * Generates visitors and stores them in a {@code VisitorList}.
     */
    class VisitorListGenerator implements Generator<VisitorList> {
        @Override
        public VisitorList generate() {
            // TODO Auto-generated method stub
            return new VisitorList();
        }
    }

    /**
     * Generator interface.
     * All generators should implement this.
     *
     * @param <T> The type of the object to be generated.
     */
    interface Generator<T> {
        T generate();
    }
}
