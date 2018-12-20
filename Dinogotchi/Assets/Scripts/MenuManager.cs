using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MenuManager : MonoBehaviour
{

    public GameObject flashText;

    // Start is called before the first frame update
    void Start()
    { // pisca o texto "iniciar..."
        InvokeRepeating("flashTheText", 0f, 0.5f);
    }

    // Update is called once per frame
    void Update()
    { // inicia ao clicar na tela
        if (Input.GetMouseButtonUp(0))
            SceneManager.LoadScene("Game");
    }


    void flashTheText() // pisca a porra do texto
    {
        if (flashText.activeInHierarchy)
            flashText.SetActive(false);

        else
            flashText.SetActive(true);
    }


}
