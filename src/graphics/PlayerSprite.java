package graphics;

public class PlayerSprite {
	// Player sprites:

	//ready
	public static Sprite playerReady1 = new Sprite(24, 51, 0, 0, SpriteSheet.ready);
	public static Sprite playerReady2 = new Sprite(24, 51, 1, 0, SpriteSheet.ready);

	public static Sprite[] playerReady = new Sprite[] { playerReady1, playerReady2 };

	//punch
	public static Sprite playerPunchRM1 = new Sprite(26, 50, 0, 0, SpriteSheet.punchRM);
	public static Sprite playerPunchRM2 = new Sprite(26, 50, 1, 0, SpriteSheet.punchRM);
	public static Sprite playerPunchRM3 = new Sprite(26, 50, 2, 0, SpriteSheet.punchRM);
	public static Sprite playerPunchRM4 = new Sprite(26, 50, 3, 0, SpriteSheet.punchRM);
	public static Sprite playerPunchRM5 = new Sprite(26, 50, 4, 0, SpriteSheet.punchRM);
	public static Sprite playerPunchRM6 = new Sprite(26, 50, 5, 0, SpriteSheet.punchRM);

	public static Sprite[] playerPunchRM = new Sprite[] { playerPunchRM1, playerPunchRM1, playerPunchRM2, playerPunchRM3, playerPunchRM3, playerPunchRM3, playerPunchRM4, playerPunchRM5, playerPunchRM6 };

	//kick
	public static Sprite playerKickLM1 = new Sprite(31, 50, 0, 0, SpriteSheet.kickLM);
	public static Sprite playerKickLM2 = new Sprite(31, 50, 1, 0, SpriteSheet.kickLM);
	public static Sprite playerKickLM3 = new Sprite(31, 50, 2, 0, SpriteSheet.kickLM);
	public static Sprite playerKickLM4 = new Sprite(31, 50, 3, 0, SpriteSheet.kickLM);
	public static Sprite playerKickLM5 = new Sprite(31, 50, 4, 0, SpriteSheet.kickLM);

	public static Sprite[] playerKickLM = new Sprite[] { playerKickLM1, playerKickLM1, playerKickLM2, playerKickLM3, playerKickLM4, playerKickLM5 };

	//dodge
	public static Sprite[] playerDodge = new Sprite[] { new Sprite(25, 49, "/Res/dodge.png") };
}
