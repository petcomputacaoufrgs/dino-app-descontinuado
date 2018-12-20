using System.Collections;
using System;
using System.Collections.Generic;
using UnityEngine;

public class Robot : MonoBehaviour
{

    [SerializeField]
    private int _hunger;
    [SerializeField]
    private int _happiness;
    [SerializeField]
    private string _name;
    
    private bool _serverTime;
    private int _clickCount;

    // Start is called before the first frame update
    void Start()
    {
        PlayerPrefs.SetString("then", "12/19/2018 00:20:12");
        updateStatus();
        if (!PlayerPrefs.HasKey("name"))
            PlayerPrefs.SetString("name", "Robot");
        _name = PlayerPrefs.GetString("name");
    }

    // Update is called once per frame
    void Update()
    {

        // seta o boolean de jump para falso ou verdadeiro a depender da posição do robo
        GetComponent<Animator> ().SetBool("jump", gameObject.transform.position.y > -2.9f); 

        if (Input.GetMouseButtonUp(0))
        { // pega posição do mouse
            Vector2 v = new Vector2(Input.mousePosition.x, Input.mousePosition.y);
            // checa se houve colisão com algum objeto da cena
            RaycastHit2D hit = Physics2D.Raycast(Camera.main.ScreenToWorldPoint(v), Vector2.zero);
         
            if (hit)
            {
                // se o robô for clikado ... 
                if (hit.transform.gameObject.tag == "robot")
                {
                    _clickCount++;
                    if (_clickCount >= 3)
                    {
                        _clickCount = 0;
                        updateHappiness(1); // aumenta a happiness 1 ponto
                        GetComponent<Rigidbody2D>().AddForce(new Vector2(0, 2000000)); // adiciona uma força fazendo com que o robô pule
                    }
                }
            }
        }

    }

    void updateStatus()
    {
        // se nao ha valor de fome salvo, inicia como 100
        if (!PlayerPrefs.HasKey("_hunger"))
        {
            _hunger = 100;
            PlayerPrefs.SetInt("_hunger", _hunger);
        } else // senao, carrega o salvo
        {
            _hunger = PlayerPrefs.GetInt("_hunger");
        }

        // se nao ha valor de felicidade salvo, inicia como 100
        if (!PlayerPrefs.HasKey("_happiness"))
        {
            _happiness = 100;
            PlayerPrefs.SetInt("_happiness", _happiness);
        }
        else // senao, carrega o salvo
        {
            _happiness = PlayerPrefs.GetInt("_happiness");
        }

        // se nao ha valor de tempo salvo, salva o atual
        if (!PlayerPrefs.HasKey("then"))
        {
            PlayerPrefs.SetString("then", getStringTime());
        }

        TimeSpan ts = getTimeSpan();

        // decresce o valor de fome de acordo com o tempo
        _hunger -= (int)(ts.TotalHours * 2);

        if (_hunger < 0)
        {
            _hunger = 0; 
        }

        // decresce o valor de felicidade de acordo com o tempo/fome
        _happiness -= (int)((100 - _hunger) * (ts.TotalHours / 5));

        if (_happiness < 0)
        {
            _happiness = 0;
        }



        if (_serverTime)
            updateServer();
        else // atualiza o valor da ultima hora jogada a cada 30s
            InvokeRepeating ("updateDevice",0f,30f);
    }

    void updateServer() // supostamente usa o tempo do server, mas ngm vai usar provavelmente
    {
    }

    void updateDevice() // atualiza o valor da ultima hora jogada (de acordo com o tempo do dispositivo)
    {
        PlayerPrefs.SetString("then", getStringTime());
    }

    TimeSpan getTimeSpan() // retorna o tempo passado desda a ultima vez que foi jogado
    {
        if (_serverTime)
            return new TimeSpan();
        else
            return DateTime.Now - Convert.ToDateTime(PlayerPrefs.GetString("then"));
    }
    string getStringTime() // retorna o tempo atual em formato de string
    {
        DateTime now = DateTime.Now;
        return now.Month + "/" + now.Day + "/" + now.Year + " " + now.Hour + ":" + now.Minute + ":" + now.Second; 
    }

    public int hunger // getters e setters da fome
    {
        get { return _hunger; }
        set { _hunger = value; }
    }

    public int happiness // getters e setters da felicidade
    {
        get { return _happiness; }
        set { _happiness = value; }
    }

    public string name // getters e setters do nome
    {
        get { return _name; }
        set { _name = value; }
    }


    public void updateHappiness(int i) // aumenta i unidades da felicidade com um limite de 100
    {
        happiness += i;
        if (happiness > 100)
        {
            happiness = 100;
        }
    }

    public void saveRobot()
    {
        if (!_serverTime)
        {
            updateDevice();
        }

        PlayerPrefs.SetInt("_hunger", _hunger);
        PlayerPrefs.SetInt("_happiness", _happiness);
    }

}
