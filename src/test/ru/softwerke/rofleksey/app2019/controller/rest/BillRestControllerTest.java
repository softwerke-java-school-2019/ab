package ru.softwerke.rofleksey.app2019.controller.rest;

//public class BillRestControllerTest extends JerseyTest {
//    final Random random = new Random();
//    final ArrayList<Bill> arr = new ArrayList<>();
//
//    @Override
//    protected Application configure() {
//        enable(TestProperties.LOG_TRAFFIC);
//        enable(TestProperties.DUMP_ENTITY);
//        return new ShopApplication();
//    }
//
//    @Before
//    public void init() {
//        for(int i = 0; i < 100; i++) {
//            Bill original = new Bill(random.nextLong(), generateList(), LocalDateTime.parse("07-05-1998 10:10:10", BillRequest.format));
//            arr.add(original);
//            Bill response;
//            try {
//                String refl = target("bill").request().post(Entity.json(original)).toString();
//                System.out.println(refl);
//            } catch (BadRequestException e) {
//                System.out.println(e.getResponse().readEntity(String.class));
//                throw e;
//            }
//            System.out.println(i);
//            //assertEquals(i, response.getId());
//        }
//    }
//
//    private <T> T getRandom(List<T> list) {
//        return list.get((int) (list.size()*random.nextFloat()));
//    }
//
//    private List<BillItem> generateList() {
//        int count = random.nextInt(6);
//        List<BillItem> items = new ArrayList<>(count);
//        for(int i = 0; i < count; i++) {
//            items.add(generateItem());
//        }
//        return items;
//    }
//
//    private BillItem generateItem() {
//        return new BillItem(random.nextLong(), random.nextInt(5), BigDecimal.valueOf(random.nextDouble()*200000));
//    }
//
//    @Test
//    public void testDateMassive() {
//        List<Bill> response = target("bill").queryParam("orderType", "date").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Bill>>(){});
//        List<Bill> expected = arr.stream().sorted(BillRequest.comparators.get("date")).collect(Collectors.toList());
//        assertEquals(expected, response);
//    }
//}