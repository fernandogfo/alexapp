package alexa.app.utils

import org.springframework.stereotype.Service

@Service
class StringToBearer {
companion object{
    fun insertBearer(token: String?): String{

        return "Bearer ${token}"
    }
}

}