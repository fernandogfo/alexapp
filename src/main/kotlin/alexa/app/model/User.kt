package alexa.app.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "USERS")
class User {
    @Id
    @GeneratedValue(generator = "UUID4")
    @GenericGenerator(
        name = "UUID4",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", insertable = false, updatable = false, nullable = false)
    val id: String? = null

    @NotNull
    @Column(name = "USER_EMAIL")
    var userEmail: String? = null

    @NotNull
    @Column(name = "TWITCH_USER_LOGIN")
    var twitchUserLogin: String? = null

    @NotNull
    @Column(name = "TWITCH_USER_ID")
    var twitchUserId: String? = null

    @Column(name = "TWITCH_USER_ACCESS_TOKEN")
    var twitchUserAccessToken: String? = null

    @Column(name = "TWITCH_USER_REFRESH_ACCESS_TOKEN")
    var twitchUserRefreshAccessToken: String? = null

    @Column(name = "DATA_CADASTRO")
    var createdAt: Date? = Date()


}