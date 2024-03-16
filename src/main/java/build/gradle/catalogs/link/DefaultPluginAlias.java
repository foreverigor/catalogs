package build.gradle.catalogs.link;

import build.gradle.catalogs.api.PluginAlias;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;

public class DefaultPluginAlias extends AbstractAliasLink implements PluginAlias {

  private final String id;
  private final String version;

  public DefaultPluginAlias(String id, String version) {
    this.id = id;
    this.version = version;
  }

  @Override
  public String getDependencyString() {
    return id + " version " + version;
  }

  @Override
  public void register(String alias, VersionCatalogBuilder catalog) {
    catalog.plugin(alias, id).version(version);
  }

}
