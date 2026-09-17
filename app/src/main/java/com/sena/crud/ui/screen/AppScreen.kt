package com.sena.crud.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.domain.model.TaskDraft
import com.sena.crud.ui.state.AppScreen
import com.sena.crud.ui.viewmodel.*

@Composable
fun AppRoot(
    auth: AuthViewModel = hiltViewModel(),
    tasks: TaskViewModel = hiltViewModel(),
    drafts: DraftViewModel = hiltViewModel()
) {
    val authState by auth.state.collectAsStateWithLifecycle()
    var screen by remember {
        mutableStateOf(if (auth.userId() != null) AppScreen.Tasks else AppScreen.Start)
    }
    val uid = auth.userId()

    LaunchedEffect(uid) {
        uid?.let {
            tasks.start(it)
            drafts.start(it)
        }
    }

    BackHandler(screen == AppScreen.Tasks || screen == AppScreen.Drafts) {
        if (screen == AppScreen.Drafts) {
            screen = AppScreen.Tasks
        } else {
            auth.logout()
            screen = AppScreen.Start
        }
    }

    when (screen) {
        AppScreen.Start -> StartScreen(
            onLogin = { screen = AppScreen.Login },
            onRegister = { screen = AppScreen.Register },
            configured = authState.isConfigured
        )

        AppScreen.Login -> LoginScreen(
            s = authState,
            back = { screen = AppScreen.Start },
            login = { e, p ->
                auth.login(e, p) { screen = AppScreen.Tasks }
            },
            register = { screen = AppScreen.Register }
        )

        AppScreen.Register -> RegisterScreen(
            s = authState,
            back = { screen = AppScreen.Start },
            register = { e, p ->
                auth.register(e, p) { screen = AppScreen.Tasks }
            },
            login = { screen = AppScreen.Login }
        )

        AppScreen.Tasks -> {
            val taskState by tasks.state.collectAsStateWithLifecycle()
            TaskListScreen(
                s = taskState,
                email = auth.email(),
                add = { t, d -> auth.userId()?.let { tasks.create(it, t, d) {} } },
                edit = { t, title, d, c -> tasks.update(t, title, d, c) {} },
                del = { t -> tasks.delete(t) {} },
                logout = {
                    auth.logout()
                    screen = AppScreen.Start
                },
                drafts = { screen = AppScreen.Drafts }
            )
        }

        AppScreen.Drafts -> {
            val draftState by drafts.state.collectAsStateWithLifecycle()
            var editor by remember { mutableStateOf<TaskDraft?>(null) }
            var show by remember { mutableStateOf(false) }

            DraftListScreen(
                s = draftState,
                back = { screen = AppScreen.Tasks },
                new = {
                    editor = null
                    show = true
                },
                edit = {
                    editor = it
                    show = true
                },
                del = { drafts.delete(it) {} },
                publish = { d ->
                    auth.userId()?.let { uid2 ->
                        tasks.create(uid2, d.title, d.description) {
                            drafts.delete(d) {}
                        }
                    }
                }
            )

            if (show) {
                DraftEditorDialog(editor, dismiss = { show = false }) { t, d ->
                    auth.userId()?.let { uid2 ->
                        drafts.save(uid2, editor?.id ?: 0, t, d) {
                            show = false
                        }
                    }
                }
            }
        }
    }
}
