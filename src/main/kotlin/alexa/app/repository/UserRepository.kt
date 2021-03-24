package alexa.app.repository

import alexa.app.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, String> {
 fun findByTwitchUserId(twitchUserId: String): Optional<User>
 fun findByUserEmail(userEmail: String): Optional<User>
}