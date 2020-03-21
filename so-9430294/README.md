A demo of single-host multi-tenancy.

Achieve multi-tenancy by using a plugin (`MultiTenantInterceptor`) that switches 'schema' or 'catalog' depending on the database.

- MySQL : Each tenant has its own 'database' and the plugin should call `setCatalog`.
- Oracle : Each tenant has its own 'schema' and the plugin should call `setSchema`.
- SQL Server : Each tenant has its own 'database' and the plugin should call `setCatalog`.


```sh
$ svn export https://github.com/harawata/mybatis-issues/trunk/so-9430294
$ cd so-9430294
$ mvn test
```
