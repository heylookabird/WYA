package com.example.agcostfu.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.example.agcostfu.users.User;

public class ClientCaller {
	static ArrayList<String> helpcommands;
	static ArrayList<User> clientUsers;
    static int currIndex;
    static String currChat;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		init();
		clientUsers = new ArrayList<User>();
        currIndex = 0;
        currChat = "";
        User harjit = new User();
        harjit.setUsername("harjit");
        harjit.setPhoneNumber("1234");
        clientUsers.add(harjit);
        User brooke = new User();
        brooke.setUsername("brooke");
        brooke.setPhoneNumber("2345");
        clientUsers.add(brooke);

        new CreateGroupClient("Test", harjit.getPhoneNumber(), harjit.getUserName());
        new AddToGroupClient(harjit.getPhoneNumber(), brooke.getPhoneNumber(), brooke.getUserName());

		while (true) {
			String input = scanner.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(input);
			String call = tokenizer.nextToken();
			if (call.startsWith("exit")) {
				scanner.close();
				System.exit(0);
			} else if (call.startsWith("help")) {
				outputHelp();
			} else if (call.startsWith("clear")) {
				new ClearClient("fake");
			} else if (call.startsWith("chat")) {
				User user = getUser(tokenizer.nextToken());
                //hi
				if (user == null) {
					printError();
					break;
				}
				String chat = "";
				String s;
				try {
					while ((s = tokenizer.nextToken()) != null) {
						chat = chat + " " + s;
					}
				} catch (Exception e) {
				}

				new AddToChatClient(user.getPhoneNumber(), user.getUserName(),
						chat);

			} else if (call.startsWith("location")) {
				String user = tokenizer.nextToken();
				String lon = tokenizer.nextToken();
				String lat = tokenizer.nextToken();

				String number = getUserNumber(user);

				new UpdatingClient(number, Double.parseDouble(lon),
						Double.parseDouble(lat));

			} else if (call.startsWith("get")) {
				String x = tokenizer.nextToken();
				if (x.startsWith("group")) {
					String next = tokenizer.nextToken();
					String number = getUserNumber(tokenizer.nextToken());

					if (number == null) {
						printError();
						break;
					}
					if (next.startsWith("chat")) {
						String chat = new GetGroupChatClient(number, currIndex).getInfoFromRequest();
                        currIndex += new StringTokenizer(chat, "\n").countTokens();
					} else if (next.startsWith("locations")) {
						new GetGroupLocationClient(number);
					} else {
						printError();
						break;
					}
				} else if (x.startsWith("invitations")) {
					String user = tokenizer.nextToken();

					User person = getUser(user);

					if (person == null) {
						printError();
						break;
					}

					new GetInvitationsClient(person.getPhoneNumber());

					System.out.println("Type which number to join ");

					String choice = scanner.nextLine();

					new AddToGroupClient(choice, person.getPhoneNumber(),
							person.getUserName());
				}

			} else if (call.startsWith("invite")) {
				String inviter = tokenizer.nextToken();
				String search = tokenizer.nextToken();

				String number = getUserNumber(inviter);

				if (number == null) {
					printError();
					break;
				}
				new InviteClient(number, search);

			} else if (call.startsWith("create")) {

				call = tokenizer.nextToken();

				if (call.startsWith("group")) {
					// format of group: group name, admin name, admin number
					String gname = tokenizer.nextToken();
					User user = getUser(tokenizer.nextToken());

					if (user == null) {
						printError();
						break;
					}
					String number = user.getPhoneNumber();
					String name = user.getUserName();

					/*
					 * if(tokenizer.nextToken() != null){ printError(); break; }
					 */

					new CreateGroupClient(gname, number, name);

				} else if (call.startsWith("user")) {
					User user = new User();
					String name = tokenizer.nextToken();
					String number = tokenizer.nextToken();

					user.setUsername(name);
					user.setPhoneNumber(number);
					clientUsers.add(user);
				} else {
					printError();
				}
			}
		}

	}

	private static User getUser(String name) {
		for (User user : clientUsers) {
			if (user.getUserName().startsWith(name)) {
				return user;
			}
		}
		return null;
	}

	private static String getUserNumber(String name) {
		for (User user : clientUsers) {
			if (user.getUserName().equals(name)) {
				return user.getPhoneNumber();
			}
		}

		System.out.println("No user with name: " + name);
		return null;
	}

	private static void printError() {
		System.out.println("Error: Wrong usage. Press help for hints");
	}

	private static void outputHelp() {
		for (String help : helpcommands) {
			System.out.println(help);
		}
	}

	private static void init() {
		helpcommands = new ArrayList<String>();
		helpcommands.add("exit : Quit application");
		helpcommands.add("create group [groupname][username]");
		helpcommands.add("create user [username][number]");
		helpcommands.add("invite [inviter][number to invite]");
		helpcommands.add("add [invitee][inviter]");
		helpcommands.add("location [username][new locationx][new locationy]");
		helpcommands.add("get group locations [username]");
		helpcommands.add("get group chat [username]");
		helpcommands.add("get invitations [username]");
		helpcommands.add("clear (clear servers)");
		helpcommands.add("chat [user] [message]");

	}
}
