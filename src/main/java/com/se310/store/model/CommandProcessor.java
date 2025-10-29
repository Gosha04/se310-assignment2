package com.se310.store.model;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.se310.store.facade.StoreFacade;
import com.se310.store.proxy.StoreServiceProxy;


// Chain of responsibility between Facade and Proxy, proxy called 'next' in chain
/**
 * CommandProcessor class implementation for processing DSL commands
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class CommandProcessor implements CommandAPI  {
    //StoreService storeService = new StoreService();
    private static final String TOKEN = "admin";
    private final StoreServiceProxy proxy = new StoreServiceProxy();
    private final StoreFacade facade = new StoreFacade();

    public void processCommand(String commandBefore) throws CommandException, StoreException {

        List<String> tokens = new ArrayList<>();
        //Split the line into tokens between spaces and quotes
        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandBefore);
        while (matcher.find())
            tokens.add(matcher.group(1).replace("\"", ""));

        System.out.println(">>> Processing DSL : " + commandBefore);

        String command = commandBefore.trim().replaceAll(" +", " ");

        if (command.toLowerCase().contains("define store")){
            facade.defineStore(tokens.get(2), tokens.get(4), tokens.get(6), TOKEN);
        } else if(command.toLowerCase().contains("show store")){
            System.out.println("<<< " + proxy.showStore(tokens.get(2),TOKEN));
        } else if(command.toLowerCase().contains("define aisle")){

            String[] location = tokens.get(2).split(":");
            facade.defineAisle(location[0],location[1], tokens.get(4), tokens.get(6),
                    AisleLocation.valueOf(tokens.get(8)),TOKEN);

        } else if(command.toLowerCase().contains("show aisle")){

            String[] location = tokens.get(2).split(":");
            System.out.println("<<< " + proxy.showAisle(location[0],location[1],TOKEN));

        } else if(command.toLowerCase().contains("define shelf")) {

            String[] location = tokens.get(2).split(":");
            facade.defineShelf(location[0],location[1],location[2], tokens.get(4), ShelfLevel.valueOf(tokens.get(6)),
                    tokens.get(8), Temperature.valueOf(tokens.get(10)), TOKEN);

        } else if(command.toLowerCase().contains("show shelf")){

            String[] location = tokens.get(2).split(":");
            System.out.println ("<<< " + proxy.showShelf(location[0], location[1], location[2], TOKEN));

        } else if(command.toLowerCase().contains("define product")) {

            facade.defineProduct(tokens.get(2), tokens.get(4), tokens.get(6),
                    tokens.get(8), tokens.get(10), Double.parseDouble(tokens.get(12)),
                    Temperature.valueOf(tokens.get(14)),TOKEN);

        } else if(command.toLowerCase().contains("show product")) {

            Product product = proxy.showProduct(tokens.get(2), TOKEN);
            System.out.println("<<< " + product);

        } else if(command.toLowerCase().contains("define inventory")) {

            String[] location = tokens.get(4).split(":");

            facade.defineInventory(tokens.get(2), location[0], location[1],
                    location[2], Integer.parseInt(tokens.get(6)), Integer.parseInt(tokens.get(8)),
                    tokens.get(12), InventoryType.valueOf(tokens.get(10)), TOKEN);

        } else if(command.toLowerCase().contains("show inventory")) {

            System.out.println("<<< " + proxy.showInventory(tokens.get(2), TOKEN));

        } else if(command.toLowerCase().contains("update inventory")) {

            Inventory inventory = proxy.updateInventory(tokens.get(2),Integer.parseInt(tokens.get(4)), TOKEN);
            System.out.println(inventory);

        } else if(command.toLowerCase().contains("define customer")){

            facade.defineCustomer(tokens.get(2), tokens.get(4), tokens.get(6),
                    CustomerType.valueOf(tokens.get(8)), tokens.get(10), tokens.get(12), null);

        } else if(command.toLowerCase().contains("update customer")){

            String[] location = tokens.get(4).split(":");
            Customer customer = proxy.updateCustomer(tokens.get(2), location[0], location[1], null);

            System.out.println("<<< " + customer);

        } else if(command.toLowerCase().contains("show customer")){

            System.out.println(proxy.showCustomer(tokens.get(2),TOKEN));

        } else if(command.toLowerCase().contains("define basket")){

            proxy.provisionBasket(tokens.get(2), TOKEN);

        } else if(command.toLowerCase().contains("assign basket")){

            proxy.assignCustomerBasket(tokens.get(4), tokens.get(2), TOKEN);

        } else if(command.toLowerCase().contains("get_customer_basket")){

            Basket basket = proxy.getCustomerBasket(tokens.get(1), TOKEN);
            System.out.println("<<< " + basket);

        } else if (command.toLowerCase().contains("add_basket_item")){

            Basket basket = proxy.addBasketProduct(tokens.get(1), tokens.get(3),
                    Integer.parseInt(tokens.get(5)), null);
            System.out.println("<<< " + basket);

        } else if (command.toLowerCase().contains("remove_basket_item")) {

            Basket basket = proxy.removeBasketProduct(tokens.get(1), tokens.get(3),
                    Integer.parseInt(tokens.get(5)), TOKEN);
            System.out.println(basket);

        } else if (command.toLowerCase().contains("clear_basket")){

            Basket basket = proxy.clearBasket(tokens.get(1),TOKEN);
            System.out.println("<<< " + basket);

        } else if (command.toLowerCase().contains("show basket_items")){

            Basket basket = proxy.showBasket(tokens.get(2),TOKEN);
            System.out.println("<<< " + basket);

        } else if (command.toLowerCase().contains("define device")){

            String[] location = tokens.get(8).split(":");
            proxy.provisionDevice(tokens.get(2), tokens.get(4),
                    tokens.get(6), location[0], location[1], TOKEN);

        } else if (command.toLowerCase().contains("show device")){

            System.out.println("<<< " + proxy.showDevice(tokens.get(2),TOKEN));

        } else if (command.toLowerCase().contains("create event")){

            proxy.raiseEvent(tokens.get(2), tokens.get(4) + " " + tokens.get(5),TOKEN);

        } else if (command.toLowerCase().contains("create_event")){

            proxy.raiseEvent(tokens.get(1), tokens.get(3) + " " + tokens.get(4) + " " + tokens.get(5),null);

        } else if (command.toLowerCase().contains("create command")){

            proxy.issueCommand(tokens.get(2), tokens.get(4) + " " + tokens.get(5),TOKEN);

        } else {
            throw new CommandException(command, "Unrecognized Command");
        }
    }

    public void processCommandFile(String fileName) {
        Path path = FileSystems.getDefault().getPath(Path.of(fileName).toAbsolutePath().toString());
        List<String> tokens = new ArrayList<>();

        //Process all the lines in the file
        try (var stream = Files.lines(path)) {
            tokens = stream
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<String> iterator = tokens.iterator();

        //Filter out any empty lines and lines that start with #
        for (int i = 0; iterator.hasNext(); i ++) {
            String temp = iterator.next();
            if(!temp.trim().startsWith("#") && !temp.trim().isEmpty()) {
                try {
                    processCommand(temp);
                } catch (CommandException e) {
                    e.setLineNumber(i + 1);
                    System.out.println("\u001B[31m" + "Failed due to: " + e.getReason() + " for Command: " + e.getCommand()
                            + " On Line Number: " + e.getLineNumber() + "\u001B[0m");
                } catch (StoreException e) {
                    System.out.println("\u001B[31m" + "Failed due to: " + e.getReason() + " for Command: " + e.getAction() + "\u001B[0m");
                }
            }
        }

    }
}
