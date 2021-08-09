package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

/**
 * Parser to create an instance of {@link SkinProvider}
 * by a given {@link SkinProviderType}.
 */
public class SkinProviderCreator {

	/**
	 * @param skinProviderType Given type to parse.
	 * @param defaultValue Default value of the {@link SkinProvider}.
	 * @return An instance of the parsed {@link SkinProvider}.
	 */
	public SkinProvider create(SkinProviderType skinProviderType, String defaultValue) {
		switch (skinProviderType) {
			case VIEWER: return SkinProvider.viewerProvider();
			case PLAYER: return SkinProvider.playerProvider(defaultValue);
			case URL: return SkinProvider.urlProvider(defaultValue);
			default: return null;
		}
	}

}