import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieTicketManagementSystem {
    public static void main(String[] args) {
        List<Movie> movies = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        TicketManager ticketManager = new TicketManager();

        boolean exit = false;
        while (!exit) {
            System.out.println("===== 电影售票管理系统 =====");
            if (!ticketManager.isLoggedIn()) {
                System.out.println("1. 管理员注册");
                System.out.println("2. 管理员登录");
            } else {
                System.out.println("1. 添加电影");
                System.out.println("2. 显示电影列表");
                System.out.println("3. 电影票查看");
                System.out.println("4. 电影票购买");
                System.out.println("5. 电影票选座");
                System.out.println("6. 电影票退票");
                System.out.println("7. 修改电影票");
                System.out.println("8. 退出");
            }
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 读取换行符

            if (!ticketManager.isLoggedIn()) {
                switch (choice) {
                    case 1:
                        ticketManager.register();
                        break;
                    case 2:
                        ticketManager.login();
                        break;
                    default:
                        System.out.println("无效的选择，请重新输入！");
                }
            } else {
                switch (choice) {
                    case 1:
                        System.out.print("请输入电影名称：");
                        String name = scanner.nextLine();
                        System.out.print("请输入电影时长（分钟）：");
                        int duration = scanner.nextInt();
                        scanner.nextLine(); // 读取换行符

                        Movie movie = new Movie(name, duration);
                        movies.add(movie);
                        System.out.println("电影添加成功！");
                        break;

                    case 2:
                        System.out.println("电影列表：");
                        for (Movie m : movies) {
                            System.out.println(m);
                        }
                        break;

                    case 3:
                        ticketManager.viewTickets();
                        break;

                    case 4:
                        System.out.print("请输入要购买的电影名称：");
                        String movieName = scanner.nextLine();
                        Movie foundMovie = null;
                        for (Movie m : movies) {
                            if (m.getName().equalsIgnoreCase(movieName)) {
                                foundMovie = m;
                                break;
                            }
                        }
                        if (foundMovie != null) {
                            ticketManager.purchaseTicket(foundMovie);
                        } else {
                            System.out.println("找不到该电影！");
                        }
                        break;

                    case 5:
                        System.out.print("请输入要选座的电影名称：");
                        String movieNam = scanner.nextLine();
                        Movie foundMovi = null;
                        for (Movie n : movies) {
                            if (n.getName().equalsIgnoreCase(movieNam)) {
                                foundMovi = n;
                                break;
                            }
                        }
                        if (foundMovi != null) {
                            ticketManager.selectSeat(foundMovi);
                        } else {
                            System.out.println("找不到该电影！");
                        }
                        break;

                    case 6:
                        System.out.print("请输入要退票的座位号：");
                        String seatNumber = scanner.nextLine();
                        ticketManager.refundTicket(seatNumber);
                        break;

                    case 7:
                        System.out.print("请输入要修改的座位号：");
                        String seatNumbe = scanner.nextLine();
                        ticketManager.modifyTicket(seatNumbe);
                        break;

                    case 8:
                        exit = true;
                        System.out.println("再见！");
                        break;

                    default:
                        System.out.println("无效的选择，请重新输入！");
                }
            }
        }
    }
}

class TicketManager {
    private static String username = "";
    private static String password = "";
    private static boolean loggedIn = false;
    private List<MovieTicket> tickets = new ArrayList<>();

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        username = scanner.nextLine();
        System.out.print("请输入密码：");
        password = scanner.nextLine();
        System.out.println("注册成功！");
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String inputUsername = scanner.nextLine();
        System.out.print("请输入密码：");
        String inputPassword = scanner.nextLine();

        if (username.equals(inputUsername) && password.equals(inputPassword)) {
            loggedIn = true;
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败，请检查用户名和密码！");
        }
    }

    public static void logout() {
        loggedIn = false;
        System.out.println("成功退出登录！");
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public void viewTickets() {
        System.out.println("电影票列表：");
        for (MovieTicket ticket : tickets) {
            System.out.println("电影：" + ticket.getMovie().getName());
            System.out.println("座位：" + ticket.getSeat());
            System.out.println("----------------------");
        }
    }


    public void purchaseTicket(Movie movie) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入座位号：");
        String seat = scanner.nextLine();
        MovieTicket ticket = new MovieTicket(movie, seat);
        tickets.add(ticket);
        System.out.println("购买成功！");
    }

    public void selectSeat(Movie movie) {
        System.out.print("请输入座位号：");
        Scanner scanner = new Scanner(System.in);
        String seat = scanner.nextLine();
        MovieTicket ticket = new MovieTicket(movie, seat);
        tickets.add(ticket);
        System.out.println("选座成功！");
    }

    public void refundTicket(String seat) {
        boolean ticketRemoved = false;
        for (MovieTicket ticket : tickets) {
            if (ticket.getSeat().equalsIgnoreCase(seat)) {
                tickets.remove(ticket);
                ticketRemoved = true;
                System.out.println("退票成功！");
                break;
            }
        }
        if (!ticketRemoved) {
            System.out.println("找不到该座位的电影票！");
        }
    }

    public void modifyTicket(String seat) {
        boolean ticketModified = false;
        for (MovieTicket ticket : tickets) {
            if (ticket.getSeat().equalsIgnoreCase(seat)) {
                System.out.print("请输入新的座位号：");
                Scanner scanner = new Scanner(System.in);
                String newSeat = scanner.nextLine();
                ticket.setSeat(newSeat);
                ticketModified = true;
                System.out.println("修改成功！");
                break;
            }
        }
        if (!ticketModified) {
            System.out.println("找不到该座位的电影票！");
        }
    }
}

class Movie {
    private String name;
    private int duration;

    public Movie(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return name + " - " + duration + "分钟";
    }
}


class MovieTicket {
    private Movie movie;
    private String seat;

    public MovieTicket(Movie movie, String seat) {
        this.movie = movie;
        this.seat = seat;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}

