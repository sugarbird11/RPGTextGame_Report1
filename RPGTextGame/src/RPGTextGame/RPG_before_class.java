package RPGTextGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RPG_before_class {
	static int hero_level, hero_power, hero_hp, hero_defense, hero_mp, hero_experience, hero_money;
	static int monster_hp, monster_defense, monster_power, monster_mp, monster_level, monster_experience, monster_money;
	static String hero_name, monster_name;
	static List<String> monster_list = new ArrayList<>();
	static List<List<Integer>> monster_stats = new ArrayList<>();
	static int len_monster_list;
	static boolean quest_buyPotion, quest_huntRaccoon, quest_huntLeopardCat, quest_levelUp; // false로 초기화

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int game_situation = 0; // 게임 현재 진행 상황 체크 (switch)
		monster_list();

		System.out.println("*********************");
		while (game_situation != -1) {

			switch (game_situation) {

			case 0: // 게임 생성 단계
				System.out.print("영웅의 이름을 입력하세요 : ");
				hero_name = sc.next();
				System.out.print("이름이 입력되었습니다.\n게임에 입장하였습니다.\n");
				hero_stats();
				game_situation = 1;
				break;

			case 1: // 스탯 표시 단계
				System.out.printf("현재 %s의 이름 : %s\n", hero_name, hero_name);
				System.out.printf("현재 %s의 레벨 : %d\n", hero_name, hero_level);
				System.out.printf("현재 %s의 힘 : %d\n", hero_name, hero_power);
				System.out.printf("현재 %s의 방어력 : %d\n", hero_name, hero_defense);
				System.out.printf("현재 %s의 HP : %d\n", hero_name, hero_hp);
				System.out.printf("현재 %s의 MP : %d\n", hero_name, hero_mp);
				System.out.printf("현재 %s의 경험치 : %d\n", hero_name, hero_experience);
				System.out.printf("현재 %s의 돈 : %d원\n", hero_name, hero_money);
				game_situation = 2;
				break;

			case 2: // 장소 선택 단계
				System.out.print("0. 현재 상태\n1. 사냥터\n2. 포션 상점\n3. 업적\n입장할 장소를 입력하세요 : ");
				int where = sc.nextInt();
				if (where == 0) {
					game_situation = 1;
				} else if (where == 1) {
					game_situation = 3;
				} else if (where == 2) {
					game_situation = 4;
				} else if (where == 3) {
					game_situation = 5;
				} else {
					System.out.println("\n그 장소로는 갈 수 없습니다.");
				}
				break;

			case 3: // 사냥터 입장
				System.out.println("사냥터에 입장하였습니다.");
				for (int i = 0; i < len_monster_list; i++) {
					System.out.printf("%d. %s\n", i + 1, monster_list.get(i));
				}

				System.out.print("전투할 상대를 입력하세요 : ");
				int fight_with = sc.nextInt();
				if (fight_with < 0 || fight_with - 1 >= len_monster_list) {
					System.out.print("\n상대할 수 없는 몬스터입니다!\n");
				} else if (fight_with == 0) {
					game_situation = 2;
				} else {
					game_situation = hunting(fight_with - 1);
				}
				break;

			case 4: // 포션 상점 입장
				System.out.println("포션 상점에 입장하셨습니다.");
				System.out.println("0. 나가기");
				System.out.println("1. 힘 증강 포션 (30원)");
				System.out.println("2. 방어력 증강 포션 (30원)");
				System.out.println("3. 경험치 증강 포션 (100원)");
				System.out.println("4. HP 증강 포션 (10원)");
				System.out.println("5. MP 증강 포션 (10원)");
				System.out.print("원하시는 물건을 입력하세요 : ");
				int potion_num = sc.nextInt();
				game_situation = potionStore_show(potion_num);
				break;

			case 5: // 업적 보기
				quest_show();
				game_situation = 2;
				break;
			}

			hero_levelup();
			System.out.println("*********************");
		}
		sc.close();
	}

	static int hero_attack() {
		return hero_level * 10 + hero_power * 30;
	}

	static void hero_attacked(int sum) {
		if (hero_defense < sum) {
			System.out.printf("%s는(은) %d의 데미지를 받았습니다.\n\n", hero_name, sum - hero_defense);
			hero_hp -= (sum - hero_defense);
		} else {
			System.out.printf("%s는(은) 데미지를 받지 않았습니다.\n\n", hero_name);
		}
	}

	static int monster_attack() {
		return monster_power;
	}

	static void monster_attacked(int sum) {
		if (monster_defense < sum) {
			System.out.printf("%s는(은) %d의 데미지를 받았습니다.\n\n", monster_name, sum - monster_defense);
			monster_hp -= (sum - monster_defense);
		} else {
			System.out.printf("%s는(은) 데미지를 받지 않았습니다.\n\n", monster_name);
		}
	}

	static void hero_stats() {
		hero_hp = 1;
		hero_level = 1;
		hero_power = 15;
		hero_hp = 80;
		hero_defense = 25;
		hero_mp = 0;
		hero_experience = 0;
		hero_money = 0;
	}

	static void monster_list() {
		len_monster_list = 2;
		List<Integer> stats = new ArrayList<>();

		// 1. 너구리
		stats = new ArrayList<>(); // 초기화, stats.clear()을 사용하면 주소값을 초기화 시키는 문제 발생
		monster_list.add("너구리");
		stats.add(100); // HP
		stats.add(0); // MP
		stats.add(1); // LV
		stats.add(20); // POWER
		stats.add(5); // DEF
		stats.add(10); // MONEY
		stats.add(10); // EXP
		monster_stats.add(stats);

		// 2. 살쾡이
		stats = new ArrayList<>();
		monster_list.add("살쾡이");
		stats.add(2000); // HP
		stats.add(0); // MP
		stats.add(5); // LV
		stats.add(100); // POWER
		stats.add(20); // DEF
		stats.add(30); // MONEY
		stats.add(50); // EXP
		monster_stats.add(stats);
	}

	static int hunting(int monster) {
		monster_name = monster_list.get(monster);
		monster_hp = monster_stats.get(monster).get(0);
		monster_mp = monster_stats.get(monster).get(1);
		monster_level = monster_stats.get(monster).get(2);
		monster_power = monster_stats.get(monster).get(3);
		monster_defense = monster_stats.get(monster).get(4);
		monster_money = monster_stats.get(monster).get(5);
		monster_experience = monster_stats.get(monster).get(6);

		System.out.printf("%s와 전투를 시작합니다.\n\n", monster_name);
		int whose_turn = 0;
		while (monster_hp > 0 && hero_hp > 0) {
			if (whose_turn == 0) {
				System.out.printf("%s의 공격입니다.\n", hero_name);
				monster_attacked(hero_attack());
				if (monster_hp <= 0) {
					System.out.printf("%s가(이) 죽었습니다.\n", monster_name);
					hero_money += monster_money;
					hero_experience += monster_experience;
					quest_achieved(2);
					return 2;
				}
			} else {
				System.out.printf("%s의 공격입니다.\n", monster_name);
				hero_attacked(monster_attack());
				if (hero_hp <= 0) {
					System.out.printf("%s가(이) 죽었습니다.\n", hero_name);
					return -1;
				}
			}
			whose_turn = 1 - whose_turn;
		}
		return -1;
	}

	static int potionStore_show(int num) {
		if (num == 0)
			return 2;
		System.out.println("\n포션 상점에서 물건 구매를 시도하는 중입니다.");
		if (num > 5 || num < 0) {
			System.out.println("유효하지 않은 값입니다.");
			return 4;

		} else if (!can_buy(num)) {
			System.out.println("돈이 부족합니다.");
			return 4;

		} else {
			System.out.println("구입이 완료되었습니다.");
			quest_achieved(1);
			switch (num) {
			case 1:
				hero_power += 3;
				hero_money -= 30;
				break;
			case 2:
				hero_defense += 3;
				hero_money -= 30;
				break;
			case 3:
				hero_experience += 50;
				hero_money -= 100;
				break;
			case 4:
				hero_hp += 50;
				hero_money -= 10;
				break;
			case 5:
				hero_mp += 50;
				hero_money -= 10;
				break;
			}
			return 1;
		}
	}

	static boolean can_buy(int num) {
		switch (num) {
		case 1, 2:
			if (hero_money >= 30)
				return true;
			break;
		case 3:
			if (hero_money >= 100)
				return true;
			break;
		case 4, 5:
			if (hero_money >= 10)
				return true;
			break;
		}
		return false;
	}

	static void hero_levelup() {
		while (hero_experience >= 80) {
			quest_achieved(3);
			hero_level += 1;
			hero_experience -= 80;
			hero_money += 100;
			System.out.printf("\n%s의 레벨이 %d이(가) 되었습니다.\n레벨업 보상으로 100원을 받았습니다.\n현재 소지금 : %d원\n", hero_name, hero_level,
					hero_money);
		}
	}

	static void quest_achieved(int event) {
		if (quest_buyPotion == false && event == 1) {
			System.out.println("\n'업적 : 포션 구매'를 달성하였습니다!\n보상으로 50의 경험치와 20원을 받았습니다.");
			hero_experience += 50;
			hero_money += 20;
			quest_buyPotion = true;
		}
		if (quest_huntRaccoon == false && event == 2 && monster_name == "너구리") {
			System.out.println("\n'업적 : 너구리 사냥'을 달성하였습니다!\n보상으로 50의 경험치와 20원을 받았습니다.");
			hero_experience += 50;
			hero_money += 20;
			quest_huntRaccoon = true;
		}
		if (quest_huntLeopardCat == false && event == 2 && monster_name == "살쾡이") {
			System.out.println("\n'업적 : 살쾡이 사냥'을 달성하였습니다!\n보상으로 100의 경험치와 50원을 받았습니다.");
			hero_experience += 50;
			hero_money += 20;
			quest_huntLeopardCat = true;
		}
		if (quest_levelUp == false && event == 3) {
			System.out.println("\n'업적 : 레벨 업'을 달성하였습니다!\n보상으로 20의 경험치와 10원을 받았습니다.");
			hero_experience += 50;
			hero_money += 20;
			quest_levelUp = true;
		}
	}

	static void quest_show() {
		System.out.printf("%s 업적 : 레벨 업\n", quest_levelUp ? "[달성!]" : "[미달성]");
		System.out.printf("%s 업적 : 너구리 사냥\n", quest_huntRaccoon ? "[달성!]" : "[미달성]");
		System.out.printf("%s 업적 : 살쾡이 사냥\n", quest_huntLeopardCat ? "[달성!]" : "[미달성]");
		System.out.printf("%s 업적 : 포션 구매\n", quest_buyPotion ? "[달성!]" : "[미달성]");
	}

}