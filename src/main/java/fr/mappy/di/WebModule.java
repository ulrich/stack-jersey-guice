package fr.mappy.di;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import fr.mappy.resource.RootResource;
import fr.mappy.service.user.UserService;
import fr.mappy.service.user.UserServiceImpl;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class WebModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class);

        bind(UserService.class).to(UserServiceImpl.class);

        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        serve("/services/api/v1/*").with(GuiceContainer.class);

        bind(RootResource.class);
    }
}
