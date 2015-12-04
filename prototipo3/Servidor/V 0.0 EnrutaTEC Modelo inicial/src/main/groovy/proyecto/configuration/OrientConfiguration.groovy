package proyecto.configuration

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration



@Configuration
class OrientConfiguration {

    @Value('${proyecto.orient.url}')
    String url

    @Value('${proyecto.orient.usr}')
    String usr

    @Value('${proyecto.orient.pwd}')
    String pwd



    @Bean
    OrientGraphFactory orientGraphFactory() {

        return new OrientGraphFactory(url, usr, pwd).setupPool(5, 15)
    }

}
