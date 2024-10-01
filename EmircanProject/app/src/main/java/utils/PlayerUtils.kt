package utils



import com.example.emircanproject.Player

object PlayerUtils {
    fun formatPlayer(player: Player): String {
        return "${player.name} - ${player.position}"
    }
}

